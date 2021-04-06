package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fusikun.com.api.dtoREQ.MenuRequest;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.utils.ConstantErrorCodes;

@Component
public class MenuValidator implements Validator {

	@Autowired
	MenuService menuService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(MenuRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MenuRequest menu = (MenuRequest) target;
		// Check duplicate name or url:
		if (menuService.countByName(menu.getName()) > 0)
			errors.rejectValue("name", ConstantErrorCodes.UNIQUE_VALUE);
		// Check valid parentId:
		if (menu.getParentId() != null) {
			if (!menuService.existsById(menu.getParentId())) {
				errors.rejectValue("parentId", ConstantErrorCodes.NOT_EXIST_DATA);
			}
		}
	}

}