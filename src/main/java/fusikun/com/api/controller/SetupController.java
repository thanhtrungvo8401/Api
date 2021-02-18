package fusikun.com.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.RoleRequest;
import fusikun.com.api.dto.UserRequest;
import fusikun.com.api.model.app.Role;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.Constant;

@RestController
@PropertySource("classpath:configure.properties")
public class SetupController {
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	RoleController roleController;

	@Autowired
	UserController userController;

	@Autowired
	Environment env;

	@GetMapping("/initial-project")
	public ResponseEntity<Object> handleSetupProject(@RequestParam("code") String code) throws Exception {
		String validCode = env.getProperty("configure.code");
		if (!code.equals(validCode)) {
			throw new Exception("Method not found!");
		}
		long totalUser = userService.count();
		if (totalUser != 0) {
			throw new Exception("Method not found!");
		}
		RoleRequest roleRequest = new RoleRequest();
		roleRequest.setRoleName(Constant.ADMIN_ROLE);
		roleRequest.setDescription("Full permission role!");
		roleController.handleCreateRole(roleRequest);
		Role role = roleService.findByRoleName(Constant.ADMIN_ROLE);

		UserRequest userRequest = new UserRequest();
		userRequest.setEmail(Constant.EMAIL);
		userRequest.setRoleId(role.getId());
		userRequest.setPassword("12345678");
		userController.handleCreateUser(userRequest);
		return ResponseEntity.ok("PROJECT SETUP SUCCESSFULLY!");
	}
}
