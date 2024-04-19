/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception.handle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {
	private int status;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;

	private ErrorCode errorCode;
	private String debugMessage;
	private List<ValidationError> validationErrors;
	private String path;

	private ErrorMessage() {
		timestamp = LocalDateTime.now();
	}

	ErrorMessage(HttpStatus status) {
		this();
		this.status = status.value();
	}

	ErrorMessage(HttpStatus status, Throwable ex) {
		this();
		this.status = status.value();
		this.errorCode = ErrorCode.E_001;
		this.debugMessage = ex.getLocalizedMessage();
	}

	ErrorMessage(HttpStatus status, ErrorCode errorCode, Throwable ex) {
		this();
		this.status = status.value();
		this.errorCode = errorCode;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
