package ro.idp.upb.ioservice.exception;

public class UsernameAlreadyExists extends RuntimeException {
    public UsernameAlreadyExists(String msg) {
        super(msg);
    }

    public UsernameAlreadyExists(String msg, Throwable cause) {
        super(msg, cause);
    }
}
