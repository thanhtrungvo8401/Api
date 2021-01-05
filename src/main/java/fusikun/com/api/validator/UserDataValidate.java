package fusikun.com.api.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.User;
import fusikun.com.api.service.UserService;
import javassist.NotFoundException;

@Component
public class UserDataValidate {

	@Autowired
	UserValidator userValidator;

	@Autowired
	UserService userService;

	public final void validate(UserRequest userRequest) throws Ex_MethodArgumentNotValidException {
		// TRYM WHITE SPACE:
		userRequest.setEmail(userRequest.getEmail());
		// VALIDATION:
		BindException errors = new BindException(userRequest, UserRequest.class.getName());
		userValidator.validate(userRequest, errors);
		if (errors.hasErrors()) {
			throw new Ex_MethodArgumentNotValidException(errors.getBindingResult());
		}
	}

	public final void validateExistById(Long id) throws NotFoundException {
		User user = userService.findById(id);
		if (user == null)
			throw new NotFoundException("User with id=" + id + "is not existed");
	}

}
