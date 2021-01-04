package fusikun.com.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.dto.UserResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.User;
import fusikun.com.api.service.UserService;
import fusikun.com.api.validator.RoleDataValidate;
import fusikun.com.api.validator.UserDataValidate;
import javassist.NotFoundException;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserDataValidate userDataValidate;

	@Autowired
	RoleDataValidate roleDataValidate;

	@PostMapping("/users/create")
	public ResponseEntity<Object> handleCreateUser(@Valid @RequestBody UserRequest userRequest)
			throws Ex_MethodArgumentNotValidException, NotFoundException {
		// CUSTOM VALIDATE:
		userDataValidate.validate(userRequest);
		User user = userRequest.getUser();
		userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
	}
}
