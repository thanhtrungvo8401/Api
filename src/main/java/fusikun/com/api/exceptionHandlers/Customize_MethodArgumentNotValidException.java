package fusikun.com.api.exceptionHandlers;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class Customize_MethodArgumentNotValidException extends BindException {
	private static final long serialVersionUID = 1530003109872328835L;

	public Customize_MethodArgumentNotValidException(BindingResult bindingResult) {
		super(bindingResult);
	}

}
