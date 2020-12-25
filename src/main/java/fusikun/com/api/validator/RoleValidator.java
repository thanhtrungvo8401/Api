package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fusikun.com.api.dto.RoleRequest;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.utils.ConstantErrorCodes;

@Component
public class RoleValidator implements Validator {
	@Autowired
	RoleService roleService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(RoleRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RoleRequest roleRequest = (RoleRequest) target;
		// check duplicate RoleName:
		if (roleService.existByRoleName(roleRequest.getRoleName())) {
			errors.rejectValue("roleName", ConstantErrorCodes.UNIQUE_VALUE);
		}
	}

}
