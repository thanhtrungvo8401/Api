package fusikun.com.api.controller;

import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.Center;
import fusikun.com.api.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.model.app.Role;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.utils.Constant;

@RestController
@RequestMapping("/api/v1")
public class InitController {
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	CenterService centerService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	Environment env;

	@GetMapping("/initial-project")
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity<Object> handleSetupProject(@RequestParam("code") String code) throws Exception {
		String validCode = env.getProperty("configure.code");
		if (!code.equals(validCode)) {
			throw new Exception("Method not found!");
		}
		long totalUser = userService.count();
		if (totalUser != 0) {
			throw new Exception("Method not found!");
		}

		Role roleAdmin = new Role();
		roleAdmin.setRoleName(Constant.ADMIN_ROLE);
		roleAdmin.setDescription("Full permission role!");

		Role roleStudent = new Role();
		roleStudent.setRoleName(Constant.STUDENT_ROLE);
		roleStudent.setDescription("Common role, Common permission only!");

		Center defaultCenter = new Center();
		defaultCenter.setCenterName(Constant.CENTER_DEFAULT);

		User userAdmin = new User();
		userAdmin.setEmail(Constant.EMAIL);
		userAdmin.setPassword(passwordEncoder.encode(Constant.PASSWORD));
		userAdmin.setCenter(defaultCenter);
		userAdmin.setRole(roleAdmin);

		roleService.save(roleAdmin);
		roleService.save(roleStudent);
		userService.save(userAdmin);
		centerService.save(defaultCenter);
		return ResponseEntity.ok("PROJECT SETUP SUCCESSFULLY!");
	}
}
