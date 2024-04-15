/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.idp.upb.ioservice.data.dto.request.RegisterRequest;
import ro.idp.upb.ioservice.data.dto.request.ValidateLoginDto;
import ro.idp.upb.ioservice.data.dto.response.GetUserDto;
import ro.idp.upb.ioservice.service.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final UserService userService;

	@GetMapping("/email/{email:.+}")
	public GetUserDto findByEmail(@PathVariable String email) {
		return userService.getUserByEmail(email);
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequest dto) {
		return userService.registerUser(dto);
	}

	@PostMapping("/validate-login")
	public ResponseEntity<GetUserDto> validateLogin(@RequestBody ValidateLoginDto dto) {
		return userService.validateLogin(dto);
	}
}
