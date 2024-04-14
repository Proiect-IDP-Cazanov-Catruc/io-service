/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.idp.upb.ioservice.data.dto.request.PostTokensDto;
import ro.idp.upb.ioservice.data.enums.TokenType;
import ro.idp.upb.ioservice.service.TokenService;

@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
public class TokenController {

	private final TokenService tokenService;

	@GetMapping("/{tokenType}/{token}")
	public ResponseEntity<?> findToken(
			@PathVariable String token, @PathVariable TokenType tokenType) {
		return tokenService.findToken(token, tokenType);
	}

	@PostMapping("/logout/{token}")
	public void logoutToken(@PathVariable String token) {
		tokenService.logoutToken(token);
	}

	@PostMapping()
	public ResponseEntity<?> saveUserTokens(@RequestBody PostTokensDto tokens) {
		return tokenService.saveUserTokens(tokens);
	}

	@PostMapping("/revoke/{token}")
	public void revokeToken(@PathVariable String token) {
		tokenService.revokeToken(token);
	}

	@GetMapping("/is-refresh/{token}")
	public ResponseEntity<?> isRefresh(@PathVariable String token) {
		return tokenService.isRefreshToken(token);
	}
}
