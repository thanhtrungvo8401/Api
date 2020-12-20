package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fusikun.com.api.dto.MenuRequest;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.utils.ConstantErrorCodes;

@Component
public class MenuDeleteValidator implements Validator {

	@Autowired
	MenuService menuService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(MenuRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MenuRequest menu = (MenuRequest) target;
		if (menu.getId() == null) {
			errors.rejectValue("id", ConstantErrorCodes.NOT_NULL);
		}
		else if (!menuService.existsById(menu.getId()) ){
			errors.rejectValue("id", ConstantErrorCodes.NOT_EXIST_DATA);
		}
	}
	
}
