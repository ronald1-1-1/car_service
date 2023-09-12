package task.aisa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import task.aisa.dto.user.RegisterUserDTO;
import task.aisa.dto.user.UserDTO;
import task.aisa.service.UserService;

@RestController
@RequestMapping("/user")
@Tag(name = "User Controller", description = "Allows you to register new users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "User registration",
            description = "Allows you to register new user"
    )
    @PostMapping("/register")
    @PreAuthorize("permitAll()")
    public UserDTO register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        return userService.registerUser(registerUserDTO);
    }
}
