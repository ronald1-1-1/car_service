package task.aisa.dto.user;

import lombok.Data;
import task.aisa.user.UserRole;

@Data
public class UserDTO {
    private String username;
    private UserRole role;
}
