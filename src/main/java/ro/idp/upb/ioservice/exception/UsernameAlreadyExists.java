/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception;

public class UsernameAlreadyExists extends RuntimeException {
	public UsernameAlreadyExists(String msg) {
		super(msg);
	}

	public UsernameAlreadyExists(String msg, Throwable cause) {
		super(msg, cause);
	}
}
