package fusikun.com.api.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.dto.UserResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.User;
import fusikun.com.api.service.UserService;
import fusikun.com.api.specificationSearch.SearchHelpers_Users;
import fusikun.com.api.specificationSearch.Specification_User;
import fusikun.com.api.utils.SortHelper;
import fusikun.com.api.validator.UserDataValidate;
import javassist.NotFoundException;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	UserDataValidate userDataValidate;

	@Autowired
	PasswordEncoder passwordEncoder;

	@GetMapping("/users")
	public ResponseEntity<Object> handleGetUsers(@RequestParam(required = false) Map<String, String> filters) {
		Specification_User userSpecification = new SearchHelpers_Users(filters).getUserSpecification();
		Pageable pageable = SortHelper.getSort(filters);
		List<User> users = userService.findAll(userSpecification, pageable);
		Long total = userService.count(userSpecification);
		List<UserResponse> userResponses = users.stream().map(user -> new UserResponse(user))
				.collect(Collectors.toList());
		return ResponseEntity.ok(new UsersManagement(userResponses, total));
	}

	@PostMapping("/users")
	public ResponseEntity<Object> handleCreateUser(@Valid @RequestBody UserRequest userRequest)
			throws Ex_MethodArgumentNotValidException, NotFoundException {
		// CUSTOM VALIDATE:
		userDataValidate.validate(userRequest);
		User user = userRequest.getUser();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(user));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable UUID id) throws NotFoundException {
		// Validate data exist or not:
		userDataValidate.validateExistById(id);
		User user = userService.findById(id);
		return ResponseEntity.ok(new UserResponse(user));
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Object> handleUpdateUserById(@Valid @RequestBody UserRequest userRequest,
			@PathVariable UUID id) throws NotFoundException, Ex_MethodArgumentNotValidException {
		// Validate data: (validate email, if password was posted => also validate)
		userRequest.setId(id);
		userDataValidate.validateExistById(id);
		userDataValidate.validate(userRequest);
		// Update user:
		User user = userService.findById(id);
		User userFromRequest = userRequest.getUser();
		user.setEmail(userFromRequest.getEmail());
		user.setRole(userFromRequest.getRole());
		userService.save(user);
		return ResponseEntity.ok(new UserResponse(user));
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Object> handleDeleteUserById(@PathVariable UUID id) throws NotFoundException {
		// VALIDATE DATA:
		userDataValidate.validateExistById(id);
		userDataValidate.validateNotDeleteYourself(id);
		User user = userService.findById(id);
		userService.delete(user);
		return ResponseEntity.ok(new UserResponse(user));
	}

	@GetMapping("/my-profile")
	public ResponseEntity<Object> handleGetUserDetail() {
		return null;
	}

	private class UsersManagement {
		List<UserResponse> list;
		Long total;

		@SuppressWarnings("unused")
		public UsersManagement() {
		}

		public UsersManagement(List<UserResponse> list, Long total) {
			this.list = list;
			this.total = total;
		}

		@SuppressWarnings("unused")
		public List<UserResponse> getList() {
			return list;
		}

		@SuppressWarnings("unused")
		public void setList(List<UserResponse> list) {
			this.list = list;
		}

		@SuppressWarnings("unused")
		public Long getTotal() {
			return total;
		}

		@SuppressWarnings("unused")
		public void setTotal(Long total) {
			this.total = total;
		}
	}
}
