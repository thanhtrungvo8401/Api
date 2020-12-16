package fusikun.com.api.exceptionHandlers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fusikun.com.api.utils.ConstantErrorCodes;

// ControllerAdvice => use for all project:
@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler {
	// COMMON ERROR:
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptionForAll(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	// AUTHENTICATE:
	@ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class, DisabledException.class })
	public final ResponseEntity<Object> handleWrongUsernamePasswordException(Exception ex, WebRequest request)
			throws Exception {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new FieldError(ConstantErrorCodes.FIELD_FORM_ERROR, ConstantErrorCodes.BAD_CREDENTIALS));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	@ExceptionHandler( MethodArgumentNotValidException.class )
	public final ResponseEntity<Object> handleValidationExceptions(Exception exeption, WebRequest request)
			throws Exception {
		MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exeption;
		List<Object> errorCodes = new LinkedList<>();
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		errorList.forEach((err) -> {
			String field = err.getObjectName();
			String code = err.getCode();
			errorCodes.add(new FieldError(field, code));
		});
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
}
