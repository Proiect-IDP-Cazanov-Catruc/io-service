package ro.idp.upb.ioservice.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import ro.idp.upb.ioservice.data.dto.request.RegisterRequest;
import ro.idp.upb.ioservice.data.dto.response.GetUserDto;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.data.enums.Role;
import ro.idp.upb.ioservice.exception.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.idp.upb.ioservice.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/email/{email:.+}")
    public GetUserDto findByEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.error("Username {} not found", email);
            return new UsernameNotFoundException("User not found");
        });
        return userToDto(user);
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

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            log.error("Username {} already exists!", dto.getEmail());
            return ResponseEntity.badRequest().build();
        }
        var user = User.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = userRepository.save(user);
        GetUserDto userDto = userToDto(savedUser);
        return ResponseEntity.ok(userDto);
    }
}
