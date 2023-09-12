package task.aisa.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import task.aisa.dto.order.CreateOrderDTO;
import task.aisa.dto.order.OrderDTO;
import task.aisa.dto.order.TimeDTO;
import task.aisa.exception.ObjectNotFoundException;
import task.aisa.service.OrderService;
import task.aisa.user.User;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Order Controller", description = "Allows you to create orders and track them")
@SecurityRequirement(name = "basicAuth")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public OrderDTO create(@Valid @RequestBody CreateOrderDTO createOrderDTO, @AuthenticationPrincipal User user)
            throws ObjectNotFoundException {
        return orderService.create(createOrderDTO, user);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<OrderDTO> getUsersAll(@AuthenticationPrincipal User user) {
        return orderService.getUsersAll(user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public OrderDTO getById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return orderService.getById(id, user);
    }

    @GetMapping("/time/{id}")
    @PreAuthorize("isAuthenticated()")
    public TimeDTO getTimeLeftById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return orderService.getTimeLeft(id, user);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<OrderDTO> getAll() {
        return orderService.getAll();
    }
}
