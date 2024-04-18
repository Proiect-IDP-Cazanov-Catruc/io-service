/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception;

import java.util.Optional;
import ro.idp.upb.ioservice.data.enums.TokenType;

public class TokenNotFoundException extends RuntimeException {
	private final Optional<String> token;
	private final Optional<TokenType> tokenType;

	public TokenNotFoundException(String token, TokenType tokenType) {
		super();
		this.token = Optional.ofNullable(token);
		this.tokenType = Optional.ofNullable(tokenType);
	}

	public TokenNotFoundException(TokenType tokenType) {
		super();
		this.token = Optional.empty();
		this.tokenType = Optional.ofNullable(tokenType);
	}

	public TokenNotFoundException(String token) {
		super();
		this.token = Optional.ofNullable(token);
		this.tokenType = Optional.empty();
	}

	public TokenNotFoundException() {
		super();
		this.token = Optional.empty();
		this.tokenType = Optional.empty();
	}

	@Override
	public String getMessage() {
		if (token.isPresent() && tokenType.isPresent()) {
			return String.format(
					"Token %s with type %s not found", token.get().substring(0, 15), tokenType.get());
		} else if (token.isPresent()) {
			return String.format("Token %s not found", token.get().substring(0, 15));
		} else if (tokenType.isPresent()) {
			return String.format("Token by token type %s not found", tokenType.get());
		}
		return "Token not found";
	}
}
