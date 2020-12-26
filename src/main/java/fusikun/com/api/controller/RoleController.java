package fusikun.com.api.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import javassist.NotFoundException;

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
		List<Menu> menus = menuService.findAll();
		handleGenerateAuthsFromRoleAndMenus(savedRole, menus);
		return ResponseEntity.ok(new RoleResponse(savedRole));
	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable Long id) throws NotFoundException {
		Role role = roleService.findRoleById(id);
		if (role == null) {
			throw new NotFoundException("Role with id=" + id + " is not existed!!");
		}
		// Check role has fully mapped with AUTHS:
		List<Auth> auths = role.getAuths();
		List<String> menusMappedNames = auths.stream().map(auth -> auth.getMenu().getName())
				.collect(Collectors.toList());
		List<Menu> menusNotMapped = menuService.findNotMappedMenus(menusMappedNames);
		if (!menusNotMapped.isEmpty()) {
			handleGenerateAuthsFromRoleAndMenus(role, menusNotMapped);
		}
		// generate Valid RETURN-DATA:
		List<Auth> updatedAuths = role.getAuths();
		List<Menu> updatedMenus = updatedAuths.stream().map(auth -> auth.getMenu()).collect(Collectors.toList());
		RoleResponse roleRes = new RoleResponse(role);
		roleRes.setMenus(updatedMenus);

		return ResponseEntity.ok(roleRes);
	}

	private List<Auth> handleGenerateAuthsFromRoleAndMenus(Role savedRole, List<Menu> menus) {
		List<Auth> auths = new LinkedList<>();
		menus.forEach(menu -> {
			Auth auth = new Auth(savedRole, menu, false);
			auths.add(auth);
		});
		return authService.saveAll(auths);
	}
}
