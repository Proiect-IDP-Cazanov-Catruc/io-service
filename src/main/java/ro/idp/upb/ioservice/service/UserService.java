package ro.idp.upb.ioservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.RegisterRequest;
import ro.idp.upb.ioservice.data.dto.response.GetUserDto;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.data.enums.Role;
import ro.idp.upb.ioservice.exception.UsernameNotFoundException;
import ro.idp.upb.ioservice.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    public GetUserDto userToDto(User user) {
        return GetUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public GetUserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("Username {} not found", email);
            return new UsernameNotFoundException("User not found");
        });
        return userToDto(user);
    }

    public ResponseEntity<?> registerUser(RegisterRequest dto) {if (userRepository.existsByEmail(dto.getEmail())) {
        log.error("Username {} already exists!", dto.getEmail());
        return ResponseEntity.badRequest().build();
    }
        var user = User.builder()
                .firstname(dto.getFirstName())
                .lastname(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        GetUserDto userDto = userToDto(savedUser);
        return ResponseEntity.ok(userDto);

    }
}
