package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import fusikun.com.api.dto.RoleRequest;
import fusikun.com.api.exceptionHandlers.Customize_MethodArgumentNotValidException;

@Component
public class RoleDataValidate {
	@Autowired
	RoleValidator roleValidator;

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
}
