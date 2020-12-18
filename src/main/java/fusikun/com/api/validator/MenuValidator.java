package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fusikun.com.api.dto.MenuRequest;
import fusikun.com.api.service.MenuServiceImpl;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.ConstantErrorCodes;

@Component
public class MenuValidator implements Validator {

	@Autowired
	MenuServiceImpl menuService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(MenuRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MenuRequest menu = (MenuRequest) target;
		if (menu.getParentId() == null) {
			if (menu.getName().contains(Constant.BLANK) || menu.getName().contains(Constant.SLASH)) {
				errors.rejectValue("name", ConstantErrorCodes.PARENT_MENU_CONTAINS_BLANK_OR_SLASH);
			}
		} else {
			if (!menuService.existsById(menu.getParentId())) {
				errors.rejectValue("parentId", ConstantErrorCodes.NOT_EXIST_DATA);
			}
		}
	}

}