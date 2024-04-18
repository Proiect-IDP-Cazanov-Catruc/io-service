/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception;

import java.util.Optional;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernameAlreadyExistsException extends RuntimeException {
	private final Optional<String> email;

	public UsernameAlreadyExistsException() {
		super();
		this.email = Optional.empty();
	}

	public UsernameAlreadyExistsException(String email) {
		super();
		this.email = Optional.ofNullable(email);
	}

	@Override
	public String getMessage() {
		if (email.isPresent()) {
			return String.format("Username %s already exists!", email.get());
		}
		return "Username already exists";
	}
}
