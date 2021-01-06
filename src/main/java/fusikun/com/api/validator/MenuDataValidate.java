package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import fusikun.com.api.dto.MenuRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.utils.SpaceUtils;

@Component
public class MenuDataValidate {

	@Autowired
	MenuValidator menuValidator;

	public final void validate(MenuRequest menuRequest) throws Ex_MethodArgumentNotValidException {
		// TRYM WHITE SPACE:
		menuRequest.setName(SpaceUtils.trymWhiteSpace(menuRequest.getName()));
		menuRequest.setUrl(SpaceUtils.trymWhiteSpace(menuRequest.getUrl()));
		// VALIDATION:
		BindException errors = new BindException(menuRequest, MenuRequest.class.getName());
		menuValidator.validate(menuRequest, errors);
		if (errors.hasErrors()) {
			throw new Ex_MethodArgumentNotValidException(errors.getBindingResult());
		}
	}
}
