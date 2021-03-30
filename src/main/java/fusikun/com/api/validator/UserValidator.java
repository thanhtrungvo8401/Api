package fusikun.com.api.validator;

import fusikun.com.api.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.model.app.User;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.ConstantErrorCodes;

@Component
public class UserValidator implements Validator {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	CenterService centerService;

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
		// check duplicate email
		if (userRequest.getId() == null) {
			// Create user:
			if (userService.countByEmail(userRequest.getEmail()) > 0) {
				errors.rejectValue("email", ConstantErrorCodes.UNIQUE_VALUE);
			}

			if (userRequest.getPassword() == null) {
				errors.rejectValue("password", ConstantErrorCodes.NOT_NULL);
			} else {
				if (StringUtils.containsWhitespace(userRequest.getPassword())) {
					errors.rejectValue("password", ConstantErrorCodes.NOT_CONTAIN_SPACE);
				}
				if (!StringUtils.hasText(userRequest.getPassword())) {
					errors.rejectValue("password", ConstantErrorCodes.NOT_EMPTY);
				}
			}
		} else {
			// Update user:
			User oldUser = userService.findById(userRequest.getId());
			if (oldUser != null && !oldUser.getEmail().equals(userRequest.getEmail())) {
				if (userService.countByEmail(userRequest.getEmail()) > 0) {
					errors.rejectValue("email", ConstantErrorCodes.UNIQUE_VALUE);
				}
			}
		}
		// check centerId:
		if (userRequest.getCenterId() != null) {
			if(centerService.findById(userRequest.getCenterId()) == null) {
				errors.rejectValue("centerId", ConstantErrorCodes.NOT_FOUND);
			}
		}
	}

}
