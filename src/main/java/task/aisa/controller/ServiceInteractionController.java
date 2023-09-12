package task.aisa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.aisa.dto.service.CreateServiceDTO;
import task.aisa.dto.service.ServiceDTO;
import task.aisa.service.ServiceInteractionService;

import java.util.List;

@RestController
@RequestMapping("/services")
@Tag(name = "Service Controller", description = "Allows you to create, delete and view services")
@SecurityRequirement(name = "basicAuth")
public class ServiceInteractionController {

    private final ServiceInteractionService serviceInteractionService;

    public ServiceInteractionController(ServiceInteractionService serviceInteractionService) {
        this.serviceInteractionService = serviceInteractionService;
    }

    @Operation(
            summary = "Get services",
            description = "Allows you to get all services"
    )
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<ServiceDTO> getAll() {
        return serviceInteractionService.getAll();
    }

    @Operation(
            summary = "Get service",
            description = "Allows you to get service by id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ServiceDTO getById(@PathVariable long id) {
        return serviceInteractionService.getById(id);
    }

    @Operation(
            summary = "Create service",
            description = "Allows you to create new service"
    )
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ServiceDTO create(@RequestBody CreateServiceDTO createServiceDTO) {
        return serviceInteractionService.create(createServiceDTO);
    }

    @Operation(
            summary = "Delete",
            description = "Allows you to delete service"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ServiceDTO delete(@PathVariable long id) {
        return serviceInteractionService.deleteById(id);
    }
}
