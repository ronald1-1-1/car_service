package task.aisa.dto.service;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class CreateServiceDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
