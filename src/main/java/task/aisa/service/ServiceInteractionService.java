package task.aisa.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import task.aisa.dto.service.CreateServiceDTO;
import task.aisa.dto.service.ServiceDTO;
import task.aisa.entity.ServiceEntity;
import task.aisa.exception.ObjectNotFoundException;
import task.aisa.repository.ServiceInteractionRepository;

import java.util.List;

@Service
public class ServiceInteractionService {

    private final ModelMapper modelMapper;
    private final ServiceInteractionRepository serviceInteractionRepository;

    public ServiceInteractionService(ModelMapper modelMapper,
                                     ServiceInteractionRepository serviceInteractionRepository) {
        this.modelMapper = modelMapper;
        this.serviceInteractionRepository = serviceInteractionRepository;
    }

    public List<ServiceDTO> getAll() {
        return serviceInteractionRepository.findAll().stream()
                .map(serviceEntity -> modelMapper.map(serviceEntity, ServiceDTO.class)).toList();
    }

    public ServiceDTO getById(Long id) throws ObjectNotFoundException {
        ServiceEntity serviceEntity = serviceInteractionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Service with id %d not found", id)));
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }

    public ServiceDTO create(CreateServiceDTO createServiceDTO) {
        ServiceEntity serviceEntity = modelMapper.map(createServiceDTO, ServiceEntity.class);
        return modelMapper.map(serviceInteractionRepository.save(serviceEntity), ServiceDTO.class);
    }

    public ServiceDTO deleteById(long id) throws ObjectNotFoundException {
        ServiceEntity serviceEntity = serviceInteractionRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        String.format("Service with id %d not found", id)
                ));
        serviceInteractionRepository.delete(serviceEntity);
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }
}
