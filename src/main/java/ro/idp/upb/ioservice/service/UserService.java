/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.PostManagerDto;
import ro.idp.upb.ioservice.data.dto.request.RegisterRequest;
import ro.idp.upb.ioservice.data.dto.request.ValidateLoginDto;
import ro.idp.upb.ioservice.data.dto.response.GetUserDto;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.data.enums.Role;
import ro.idp.upb.ioservice.exception.UsernameNotFoundException;
import ro.idp.upb.ioservice.repository.UserRepository;

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
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.email(user.getEmail())
				.role(user.getRole())
				.build();
	}

	public User findUserByEmail(String email) {
		return userRepository
				.findByEmail(email)
				.orElseThrow(
						() -> {
							log.error("Username {} not found", email);
							return new UsernameNotFoundException("User not found");
						});
	}

	public GetUserDto getUserByEmail(String email) {
		User user = findUserByEmail(email);
		return userToDto(user);
	}

	public ResponseEntity<?> registerUser(RegisterRequest dto) {
		if (userRepository.existsByEmail(dto.getEmail())) {
			log.error("Username {} already exists!", dto.getEmail());
			return ResponseEntity.badRequest().build();
		}
		var user =
				User.builder()
						.firstName(dto.getFirstName())
						.lastName(dto.getLastName())
						.email(dto.getEmail())
						.password(passwordEncoder.encode(dto.getPassword()))
						.role(Role.USER)
						.build();
		var savedUser = userRepository.save(user);
		GetUserDto userDto = userToDto(savedUser);
		return ResponseEntity.ok(userDto);
	}

	public ResponseEntity<GetUserDto> validateLogin(final ValidateLoginDto dto) {
		final User user = findUserByEmail(dto.getEmail());
		if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return ResponseEntity.ok(userToDto(user));
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	public ResponseEntity<?> createManager(PostManagerDto dto) {
		if (userRepository.existsByEmail(dto.getEmail())) {
			log.error("[MANAGER CREATE] Email {} already exists!", dto.getEmail());
			return ResponseEntity.badRequest().build();
		}
		var user =
				User.builder()
						.firstName(dto.getFirstName())
						.lastName(dto.getLastName())
						.email(dto.getEmail())
						.password(passwordEncoder.encode("manager"))
						.role(Role.MANAGER)
						.build();
		var savedUser = userRepository.save(user);
		return ResponseEntity.ok(userToDto(savedUser));
	}
}
