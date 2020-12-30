package fusikun.com.api.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import fusikun.com.api.dto.RoleRequest;
import fusikun.com.api.exceptionHandlers.MethodNotAllowedException;
import fusikun.com.api.exceptionHandlers.Customize_MethodArgumentNotValidException;
import fusikun.com.api.model.Role;
import fusikun.com.api.model.User;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import javassist.NotFoundException;

@Component
public class RoleDataValidate {
	@Autowired
	RoleValidator roleValidator;

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	public final void validate(RoleRequest roleRequest) throws Customize_MethodArgumentNotValidException {
		// TRYM WHITE SPACE:
		roleRequest.setDescription(roleRequest.getDescription());
		roleRequest.setRoleName(roleRequest.getRoleName());
		// VALIDATION:
		BindException errors = new BindException(roleRequest, RoleRequest.class.getName());
		roleValidator.validate(roleRequest, errors);
		if (errors.hasErrors()) {
			throw new Customize_MethodArgumentNotValidException(errors.getBindingResult());
		}
	}

	public final void validateExistById(Long id) throws NotFoundException {
		Role role = roleService.findRoleById(id);
		if (role == null) {
			throw new NotFoundException("Role with id=" + id + " is not existed!!");
		}
	}

	public final void validateRoleIsUsedByUser(Long id) {
		List<User> users = userService.findByRoleId(id);
		if (!users.isEmpty()) {
			throw new MethodNotAllowedException("Role is used!");
		}
	}
}
