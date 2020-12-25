package fusikun.com.api.controller;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.RoleRequest;
import fusikun.com.api.dto.RoleResponse;
import fusikun.com.api.exceptionHandlers.Customize_MethodArgumentNotValidException;
import fusikun.com.api.model.Auth;
import fusikun.com.api.model.Menu;
import fusikun.com.api.model.Role;
import fusikun.com.api.service.AuthService;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.validator.RoleDataValidate;

@RestController
public class RoleController {

	@Autowired
	RoleDataValidate roleDataValidate;

	@Autowired
	RoleService roleService;

	@Autowired
	MenuService menuService;

	@Autowired
	AuthService authService;

	@PostMapping("/roles/create")
	public ResponseEntity<Object> handleCreateRole(@Valid @RequestBody RoleRequest roleRequest)
			throws Customize_MethodArgumentNotValidException {
		// CUSTOM VALIDATE:
		roleDataValidate.validate(roleRequest);
		// SAVE ROLE:
		Role role = roleRequest.getRole();
		Role savedRole = roleService.save(role);
		handleGenerateAuthsFromRole(savedRole);
		return ResponseEntity.ok(new RoleResponse(savedRole));
	}

	private void handleGenerateAuthsFromRole(Role savedRole) {
		List<Menu> menus = menuService.findAll();
		List<Auth> auths = new LinkedList<>();
		menus.forEach(menu -> {
			Auth auth = new Auth(savedRole, menu, false);
			auths.add(auth);
		});
		authService.saveAll(auths);
	}
}
