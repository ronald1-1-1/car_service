package task.aisa.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import task.aisa.dto.user.RegisterUserDTO;
import task.aisa.dto.user.UserDTO;
import task.aisa.entity.UserEntity;
import task.aisa.exception.UsernameAlreadyExistsException;
import task.aisa.repository.UserRepository;
import task.aisa.user.User;
import task.aisa.user.UserRole;

@Service
@Primary
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(ModelMapper modelMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));
        return modelMapper.map(userEntity, User.class);
    }

    public UserDTO registerUser(RegisterUserDTO registerUserDTO) throws UsernameAlreadyExistsException {
        if (userRepository.existsById(registerUserDTO.getUsername())) {
            throw new UsernameAlreadyExistsException(
                    String.format("Username %s already exists", registerUserDTO.getUsername()));
        }
        UserEntity userEntity = modelMapper.map(registerUserDTO, UserEntity.class);
        userEntity.setRole(UserRole.USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserDTO.class);
    }

}
