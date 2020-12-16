package fusikun.com.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.exceptionHandlers.Customize_MethodArgumentNotValidException;
import fusikun.com.api.model.Menu;
import fusikun.com.api.validator.MenuValidator;

@RestController
public class MenuController {

	@Autowired
	MenuValidator menuValidator;

	@GetMapping("/menu-actions")
	public ResponseEntity<Object> getMenuActions() {
		return ResponseEntity.ok("List MENU");
	}

	@PostMapping("/menu-actions/create")
	public ResponseEntity<Object> createMenuActions(@Valid @RequestBody Menu menu)
			throws Customize_MethodArgumentNotValidException {
		
		BindException errors = new BindException(menu, "menu");
		menuValidator.validate(menu, errors);
		if (errors.hasErrors()) {
			throw new Customize_MethodArgumentNotValidException(errors.getBindingResult());
		}
		
		return ResponseEntity.ok(menu);
	}
}
