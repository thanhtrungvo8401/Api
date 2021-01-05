package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.model.User;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.ConstantErrorCodes;
import fusikun.com.api.utils.WhiteSpaceUtils;

@Component
public class UserValidator implements Validator {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(UserRequest.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserRequest userRequest = (UserRequest) target;
		// check roleId exist:
		if (roleService.findRoleById(userRequest.getRoleId()) == null) {
			errors.rejectValue("roleId", ConstantErrorCodes.NOT_FOUND);
		}
		if (WhiteSpaceUtils.isContainSpace(userRequest.getUsername())) {
			errors.rejectValue("username", ConstantErrorCodes.NOT_CONTAIN_SPACE);
		}
		// check duplicate userName, email
		if (userRequest.getId() == null) {
			// Create user:
			if (userService.countByUsername(userRequest.getUsername()) > 0) {
				errors.rejectValue("username", ConstantErrorCodes.UNIQUE_VALUE);
			}
			if (userService.countByEmail(userRequest.getEmail()) > 0) {
				errors.rejectValue("email", ConstantErrorCodes.UNIQUE_VALUE);
			}
		} else {
			// Update user:
			User oldUser = userService.findById(userRequest.getId());
			if (oldUser != null && !oldUser.getUsername().equals(userRequest.getUsername())) {
				if (userService.countByUsername(userRequest.getUsername()) > 0) {
					errors.rejectValue("username", ConstantErrorCodes.UNIQUE_VALUE);
				}
			}
			if (oldUser != null && !oldUser.getEmail().equals(userRequest.getEmail())) {
				if (userService.countByEmail(userRequest.getEmail()) > 0) {
					errors.rejectValue("email", ConstantErrorCodes.UNIQUE_VALUE);
				}
			}
		}
	}

}
