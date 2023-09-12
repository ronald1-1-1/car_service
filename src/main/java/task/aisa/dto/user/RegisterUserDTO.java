package task.aisa.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
