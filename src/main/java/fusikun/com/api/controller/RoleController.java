package fusikun.com.api.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dto.AuthResponse;
import fusikun.com.api.dto.MenuResponse;
import fusikun.com.api.dto.RoleRequest;
import fusikun.com.api.dto.RoleResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.Auth;
import fusikun.com.api.model.app.Menu;
import fusikun.com.api.model.app.Role;
import fusikun.com.api.service.AuthService;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.validator.RoleDataValidate;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

	@Autowired
	RoleDataValidate roleDataValidate;

	@Autowired
	RoleService roleService;

	@Autowired
	MenuService menuService;

	@Autowired
	AuthService authService;

	@GetMapping("/roles")
	public ResponseEntity<Object> getAllRoles() {
		List<Role> roles = roleService.findAll();
		List<RoleResponse> rolesRes = roles.stream().map(RoleResponse::new).collect(Collectors.toList());
		Long total = roleService.count();
		return ResponseEntity.ok(new RoleManagement(rolesRes, total));
	}

	@PostMapping("/roles")
	public ResponseEntity<Object> handleCreateRole(@Valid @RequestBody RoleRequest roleRequest)
			throws Ex_MethodArgumentNotValidException {
		// CUSTOM VALIDATE:
		roleDataValidate.validate(roleRequest);
		// SAVE ROLE:
		Role role = roleRequest.getRole();
		Role savedRole = roleService.save(role);
		List<Menu> menus = menuService.findAll();
		handleGenerateAuthsFromRoleAndMenus(savedRole, menus);
		return ResponseEntity.status(HttpStatus.CREATED).body(new RoleResponse(savedRole));
	}

	@GetMapping("/roles/{id}")
	public ResponseEntity<Object> getRoleById(@PathVariable UUID id) throws NotFoundException {
		// VALIDATE DATA IS EXIST OR NOT:
		roleDataValidate.validateExistById(id);
		Role role = roleService.findRoleById(id);
		// Check role has fully mapped with AUTHS:
		List<Auth> auths = role.getAuths();
		List<String> menusMappedNames = auths.stream().map(auth -> auth.getMenu().getName())
				.collect(Collectors.toList());

		List<Menu> menusNotMapped;
		if (!menusMappedNames.isEmpty()) {
			menusNotMapped = menuService.findNotMappedMenus(menusMappedNames);
		} else {
			menusNotMapped = menuService.findAll();
		}

		if (!menusNotMapped.isEmpty()) {
			List<Auth> authAdds = handleGenerateAuthsFromRoleAndMenus(role, menusNotMapped);
			auths.addAll(authAdds);
		}
		// generate Valid RETURN-DATA:
		List<AuthResponse> updatedAuthResponses = auths.stream().map((auth) -> {
			MenuResponse menuResponse = new MenuResponse(auth.getMenu());
			return new AuthResponse(auth, menuResponse);
		}).collect(Collectors.toList());
		RoleResponse roleRes = new RoleResponse(role);
		roleRes.setAuths(updatedAuthResponses);

		return ResponseEntity.ok(roleRes);
	}

	@PutMapping("/roles/{id}")
	public ResponseEntity<Object> handleUpdateRoleById(@Valid @RequestBody RoleRequest roleRequest,
			@PathVariable UUID id) throws Ex_MethodArgumentNotValidException, NotFoundException {
		// CUSTOM VALIDATE:
		roleRequest.setId(id);
		roleDataValidate.validateExistById(roleRequest.getId());
		roleDataValidate.validate(roleRequest);
		// SAVE ROLE:
		Role oldRole = roleService.findRoleById(id);
		Role role = roleRequest.getRole();
		oldRole.setRoleName(role.getRoleName());
		oldRole.setDescription(role.getDescription());
		Role saveRole = roleService.save(oldRole);
		return ResponseEntity.ok(new RoleResponse(saveRole));
	}

	@DeleteMapping("/roles/{id}")
	public ResponseEntity<Object> handleDeleteRoleById(@PathVariable UUID id) throws NotFoundException {
		// VALIDATE DATA IS EXIST OR NOT:
		roleDataValidate.validateExistById(id);
		roleDataValidate.validateRoleIsUsedByUser(id);
		Role role = roleService.findRoleById(id);
		roleService.delete(role);
		return ResponseEntity.ok(new RoleResponse(role));
	}

	private List<Auth> handleGenerateAuthsFromRoleAndMenus(Role savedRole, List<Menu> menus) {
		List<Auth> auths = new LinkedList<>();
		menus.forEach(menu -> {
			Auth auth = new Auth(savedRole, menu, false);
			auths.add(auth);
		});
		return authService.saveAll(auths);
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	private class RoleManagement {
		List<RoleResponse> list;
		Long total;
	}
}
