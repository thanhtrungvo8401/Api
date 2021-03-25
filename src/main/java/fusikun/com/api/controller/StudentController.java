package fusikun.com.api.controller;

import javax.validation.Valid;

import fusikun.com.api.model.app.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.dto.UserResponse;
import fusikun.com.api.dto.User_StudentRequest;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.Role;
import fusikun.com.api.model.app.User;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.validator.UserDataValidate;

/**
 * @author thtrungvo This controller is a copy of UserController, BUT: 1) Just
 *         has some method for none login-user to create student. 2) RoleId is
 *         not required, Its always Role-Student.
 */
@RestController
@RequestMapping("/api/common/v1")
public class StudentController {
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserDataValidate userDataValidate;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/students")
	public ResponseEntity<Object> handleCreateStudent(@Valid @RequestBody User_StudentRequest studentRequest)
			throws Ex_MethodArgumentNotValidException {
		Role roleStudent = roleService.findByRoleName(Constant.STUDENT_ROLE);
		if (roleStudent != null) {
			studentRequest.setRoleId(roleStudent.getId());
		}
		UserRequest userRequest = studentRequest.getUserRequest();
		// USER VALIDATE:
		userDataValidate.validate(userRequest);
		User user = userRequest.getUser();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
	}

	@GetMapping("/my-profile")
	public ResponseEntity<Object> handleGetUserDetail() {
		JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userService.findById(jwtUserDetails.getId());
		return ResponseEntity.ok(new UserResponse(user));
	}
}
