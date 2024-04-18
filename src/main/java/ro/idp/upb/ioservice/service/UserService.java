/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.service;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.idp.upb.ioservice.data.dto.request.PostManagerDto;
import ro.idp.upb.ioservice.data.dto.request.RegisterRequest;
import ro.idp.upb.ioservice.data.dto.request.ValidateLoginDto;
import ro.idp.upb.ioservice.data.dto.response.GetUserDto;
import ro.idp.upb.ioservice.data.entity.User;
import ro.idp.upb.ioservice.data.enums.Role;
import ro.idp.upb.ioservice.exception.InvalidCredentialsException;
import ro.idp.upb.ioservice.exception.UsernameAlreadyExistsException;
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
							return new UsernameNotFoundException(email);
						});
	}

	public GetUserDto getUserByEmail(String email) {
		log.info("Getting user dto by email {}...", email);
		User user = findUserByEmail(email);
		log.info("Got user dto by email {}, userId {}!", email, user.getId());
		return userToDto(user);
	}

	public GetUserDto registerUser(RegisterRequest dto) {
		log.info(
				"Registering user [Firstname: {}], [Lastname: {}], [Email: {}]...",
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail());
		if (userRepository.existsByEmail(dto.getEmail())) {
			log.error("Username {} already exists!", dto.getEmail());
			throw new UsernameAlreadyExistsException(dto.getEmail());
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
		log.info(
				"Registered user [Firstname: {}], [Lastname: {}], [Email: {}], associated id: {}",
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail(),
				savedUser.getId());
		return userDto;
	}

	public GetUserDto validateLogin(final ValidateLoginDto dto) {
		log.info("Validating login request for email {}...", dto.getEmail());
		final User user = findUserByEmail(dto.getEmail());
		if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			log.info("Login request is validated for email {}!", dto.getEmail());
			return userToDto(user);
		} else {
			log.error("Invalid credentials for login request email {}!", dto.getEmail());
			throw new InvalidCredentialsException();
		}
	}

	public GetUserDto createManager(PostManagerDto dto) {
		log.info(
				"Creating manager [Firstname: {}], [Lastname: {}], [Email: {}]...",
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail());
		if (userRepository.existsByEmail(dto.getEmail())) {
			log.error("[MANAGER CREATE] Email {} already exists!", dto.getEmail());
			throw new UsernameAlreadyExistsException(dto.getEmail());
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
		log.info(
				"Created manager [Firstname: {}], [Lastname: {}], [Email: {}], associated id: {}!",
				dto.getFirstName(),
				dto.getLastName(),
				dto.getEmail(),
				savedUser.getId());
		return userToDto(savedUser);
	}
}
