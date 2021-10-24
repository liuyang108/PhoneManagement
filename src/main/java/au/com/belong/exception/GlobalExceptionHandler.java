package au.com.belong.exception;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionResponse> resourceNotFound(ResourceNotFoundException ex) {
		return new ResponseEntity<ExceptionResponse>(getExceptionResponse(ex, HttpStatus.NOT_FOUND.value()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<ExceptionResponse> constraintViolationException(ConstraintViolationException ex) {
		return new ResponseEntity<ExceptionResponse>(getExceptionResponse(ex, HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<ExceptionResponse> handleConflict(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<ExceptionResponse>(getExceptionResponse(ex, HttpStatus.BAD_REQUEST.value()),
				HttpStatus.BAD_REQUEST);
	}
	
	private ExceptionResponse getExceptionResponse(RuntimeException ex, int status) {
		ExceptionResponse response = new ExceptionResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setStatus(status);
		response.setError(ex.getMessage());
		return response;
	}
}
