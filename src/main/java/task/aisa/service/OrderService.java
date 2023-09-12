package task.aisa.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import task.aisa.dto.order.CreateOrderDTO;
import task.aisa.dto.order.OrderDTO;
import task.aisa.dto.order.TimeDTO;
import task.aisa.dto.service.ServiceDTO;
import task.aisa.entity.OrderEntity;
import task.aisa.entity.ServiceEntity;
import task.aisa.entity.UserEntity;
import task.aisa.exception.ForbiddenException;
import task.aisa.exception.ObjectNotFoundException;
import task.aisa.repository.OrderRepository;
import task.aisa.repository.ServiceInteractionRepository;
import task.aisa.repository.UserRepository;
import task.aisa.user.User;
import task.aisa.user.UserRole;

import java.util.List;

@Service
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final ServiceInteractionRepository serviceInteractionRepository;
    private final UserRepository userRepository;

    public OrderService(ModelMapper modelMapper,
                        OrderRepository orderRepository,
                        ServiceInteractionRepository serviceInteractionRepository,
                        UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.serviceInteractionRepository = serviceInteractionRepository;
        this.userRepository = userRepository;
    }

    public OrderDTO create(CreateOrderDTO createOrderDTO, User user) throws ObjectNotFoundException {
        ServiceEntity serviceEntity = serviceInteractionRepository.findById(createOrderDTO.getServiceId())
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Service with id %d not found", createOrderDTO.getServiceId())));
        UserEntity userEntity = userRepository.findById(user.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with id %s not found", user.getUsername())));
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setService(serviceEntity);
        orderEntity.setUsr(userEntity);
        orderEntity.setDate(createOrderDTO.getOrderDate());
        return mapToDTO(orderRepository.save(orderEntity));
    }

    public List<OrderDTO> getUsersAll(User user) {
        UserEntity userEntity = userRepository.findById(user.getUsername())
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("User with id %s not found", user.getUsername())));
        return userEntity.getOrders().stream().map(this::mapToDTO).toList();
    }

    public OrderDTO getById(long id, User user) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Order with id %s not found", id)));
        if (hasAccess(orderEntity, user)) {
            return modelMapper.map(orderEntity, OrderDTO.class);
        } else {
            throw new ForbiddenException();
        }
    }

    public TimeDTO getTimeLeft(long id, User user) {
        OrderDTO orderDTO = getById(id, user);

        long timeUp = orderDTO.getDate().getTime();
        long diff = timeUp - System.currentTimeMillis();

        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String timeLeft = diffDays + " days " +
                diffHours + " hours " +
                diffMinutes + " minutes";

        return new TimeDTO(timeLeft);
    }

    public List<OrderDTO> getAll() {
        return orderRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    private OrderDTO mapToDTO(OrderEntity orderEntity) {
        OrderDTO orderDTO = modelMapper.map(orderEntity, OrderDTO.class);
        orderDTO.setService(modelMapper.map(orderEntity.getService(), ServiceDTO.class));
        return orderDTO;
    }

    private boolean hasAccess(OrderEntity orderEntity, User user) {
        return user.getAuthorities().stream().anyMatch(grantedAuthority ->
                grantedAuthority.getAuthority().equals(UserRole.ADMIN.getRole()))
                || user.getUsername().equals(orderEntity.getUsr().getUsername());
    }
}
