package fusikun.com.api.exceptionHandlers;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
public class Handler_Exceptions extends ResponseEntityExceptionHandler {
	// COMMON ERROR 500
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptionForAll(Exception ex, WebRequest request){
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.INTERNAL_SERVER_ERROR));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	// NOT_FOUND EXCEPTION 404
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request){
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.NOT_FOUND));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}
	// ACCESS DENIED 403
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request){
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.ACCESS_DENIED));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionResponse);
	}

	// METHOD_NOT_ALLOW_EXCEPTION 400
	@ExceptionHandler(Ex_MethodNotAllowedException.class)
	public final ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.METHOD_NOT_ALLOWED));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exceptionResponse);
	}

	@ExceptionHandler(Ex_NotAllowDeleteYourSelf.class)
	public final ResponseEntity<Object> handleNotAllowDelete(Exception ex, WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.NOT_DELETE_YOURSELF));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(exceptionResponse);
	}

	// AUTHENTICATE 400
	@ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class, DisabledException.class })
	public final ResponseEntity<Object> handleAuthenticationException(Exception ex,
																	  WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.BAD_CREDENTIALS));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	// VALIDATION 400
	// -- SpringValidation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		for (ObjectError err : ex.getBindingResult().getAllErrors()) {
			String field = ((org.springframework.validation.FieldError) err).getField();
			String code = err.getDefaultMessage();
			errorCodes.add(new Ob_FieldError(field, code));
		}
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.BAD_REQUEST));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	// customValidation:
	@ExceptionHandler({ Ex_MethodArgumentNotValidException.class })
	public final ResponseEntity<Object> handleExceptionsCustomValidation(Exception exeption, WebRequest request)
			{
		BindException ex = (BindException) exeption;
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		List<Object> errorCodes = new LinkedList<>();
		errorList.forEach((err) -> {
			String field = ((org.springframework.validation.FieldError) err).getField();
			String code = err.getCode();
			errorCodes.add(new Ob_FieldError(field, code));
		});
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.BAD_REQUEST));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}

	// OVER_RANGE EXCEPTION
	@ExceptionHandler({ Ex_OverRangeException.class })
	public final ResponseEntity<Object> handleExceptionOverRange(Exception ex, WebRequest request) {
		List<Object> errorCodes = new LinkedList<>();
		errorCodes.add(new Ob_FieldError(ConstantErrorCodes.ANNOUNCE, ConstantErrorCodes.OVER_RANGE_EXCEPTION));
		Ob_ExceptionResponse exceptionResponse = new Ob_ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false), errorCodes);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
	}
}
