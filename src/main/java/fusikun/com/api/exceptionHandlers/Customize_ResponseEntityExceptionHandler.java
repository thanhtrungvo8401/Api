package fusikun.com.api.exceptionHandlers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fusikun.com.api.utils.ConstantErrorCodes;
import javassist.NotFoundException;

// ControllerAdvice => use for all project:
@ControllerAdvice
@RestController
public class Customize_ResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	// COMMON ERROR:
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptionForAll(Exception ex, WebRequest request) throws Exception {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.INTERNAL_SERVER_ERROR));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	// NOT_FOUND EXCEPTION:
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) throws Exception {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.NOT_FOUND));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}

	// METHOD_NOT_ALLOW_EXCEPTION:
	@ExceptionHandler(MethodNotAllowedException.class)
	public final ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.METHOD_NOT_ALLOWED));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exceptionResponse);
	}

	// AUTHENTICATE:
	@ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class, DisabledException.class })
	public final ResponseEntity<Object> handleWrongUsernamePasswordException(Exception ex, WebRequest request)
			throws Exception {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.BAD_CREDENTIALS));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	// VALIDATION:
	// -- SpringValidation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		for (ObjectError err : ex.getBindingResult().getAllErrors()) {
			String field = ((org.springframework.validation.FieldError) err).getField();
			String code = err.getDefaultMessage();
			errorCodes.add(new FieldError(field, code));
		}
		errorCodes.add(new FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.BAD_REQUEST));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	// customValidation:
	@ExceptionHandler({ Customize_MethodArgumentNotValidException.class })
	public final ResponseEntity<Object> handleExceptionsCustomValidation(Exception exeption, WebRequest request)
			throws Exception {
		BindException ex = (BindException) exeption;
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		List<Object> errorCodes = new LinkedList<>();
		errorList.forEach((err) -> {
			String field = ((org.springframework.validation.FieldError) err).getField();
			String code = err.getCode();
			errorCodes.add(new FieldError(field, code));
		});
		errorCodes.add(new FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.BAD_REQUEST));
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
}
