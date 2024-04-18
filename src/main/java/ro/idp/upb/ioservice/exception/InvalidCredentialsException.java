/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception;

public class InvalidCredentialsException extends RuntimeException {
	public InvalidCredentialsException() {
		super();
	}

	public String getMessage() {
		return "Invalid credentials";
	}
}
