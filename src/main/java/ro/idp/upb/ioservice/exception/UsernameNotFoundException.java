/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception;

public class UsernameNotFoundException extends RuntimeException {
	public UsernameNotFoundException(String msg) {
		super(msg);
	}

	public UsernameNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
