package fusikun.com.api.exceptionHandlers;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class Ex_MethodArgumentNotValidException extends BindException {
	private static final long serialVersionUID = 1530003109872328835L;

	public Ex_MethodArgumentNotValidException(BindingResult bindingResult) {
		super(bindingResult);
	}

}
