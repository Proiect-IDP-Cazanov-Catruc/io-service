/* Ionel Catruc 343C3, Veaceslav Cazanov 343C3 | IDP IO-SERVICE | (C) 2024 */
package ro.idp.upb.ioservice.exception.handle;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ro.idp.upb.ioservice.exception.*;

@ControllerAdvice
public class ExceptionHandling {

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorMessage> handleInvalidCredentials(
			InvalidCredentialsException ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.UNAUTHORIZED, ErrorCode.E_002, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> handleValidationErrors(
			MethodArgumentNotValidException ex, HttpServletRequest request) {
		List<ValidationError> validationErrors =
				ex.getBindingResult().getFieldErrors().stream()
						.map(
								fieldError ->
										ValidationError.builder()
												.field(fieldError.getField())
												.message(fieldError.getDefaultMessage())
												.build())
						.toList();

		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.E_201, ex, request);
		errorMessage.setValidationErrors(validationErrors);
		errorMessage.setDebugMessage(ex.getTypeMessageCode());

		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(NotRefreshTokenException.class)
	public ResponseEntity<ErrorMessage> handleNotRefreshTokenException(
			NotRefreshTokenException ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.E_104, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleCategoryNotFound(
			CategoryNotFoundException ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.NOT_FOUND, ErrorCode.E_102, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleProductNotFoundException(
			ProductNotFoundException ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.NOT_FOUND, ErrorCode.E_103, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleUsernameNotFoundException(
			UsernameNotFoundException ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.NOT_FOUND, ErrorCode.E_100, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<ErrorMessage> handleUsernameAlreadyExistsException(
			UsernameAlreadyExistsException ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.CONFLICT, ErrorCode.E_101, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> exception(Exception ex, HttpServletRequest request) {
		ErrorMessage errorMessage =
				buildErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.E_001, ex, request);
		return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(errorMessage.getStatus()));
	}

	private ErrorMessage buildErrorMessage(
			HttpStatus code, ErrorCode errorCode, Exception e, HttpServletRequest request) {
		ErrorMessage error = new ErrorMessage(code, errorCode, e);
		error.setPath(request.getRequestURI());
		return error;
	}
}
