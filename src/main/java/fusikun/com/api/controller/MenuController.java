package fusikun.com.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.MenuRequest;
import fusikun.com.api.dto.MenuResponse;
import fusikun.com.api.exceptionHandlers.Customize_MethodArgumentNotValidException;
import fusikun.com.api.model.Menu;
import fusikun.com.api.service.MenuService;
import fusikun.com.api.validator.MenuDataValidate;

@RestController
public class MenuController {

	@Autowired
	MenuDataValidate menuDataValidate;
	
	@Autowired
	MenuService menuService;

	@GetMapping("/menu-actions")
	public ResponseEntity<Object> getMenuActions() {
		return ResponseEntity.ok("List MENU");
	}

	@PostMapping("/menu-actions/create")
	public ResponseEntity<Object> createMenuActions(@Valid @RequestBody MenuRequest menuRequest)
			throws Customize_MethodArgumentNotValidException {
		menuDataValidate.validate(menuRequest);
		Menu menu = menuRequest.getMenu();
		Menu savedMenu = menuService.save(menu);
		MenuResponse menuResponse = new MenuResponse(savedMenu);
		return ResponseEntity.ok(menuResponse);
	}
}
