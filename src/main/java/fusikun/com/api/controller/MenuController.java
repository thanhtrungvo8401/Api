package fusikun.com.api.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.model.Menu;

@RestController
public class MenuController {
	@GetMapping("/menu-actions")
	public ResponseEntity<Object> getMenuActions() {
		return ResponseEntity.ok("List MENU");
	}
	
	@PostMapping("/menu-actions/create")
	public ResponseEntity<Object> createMenuActions(@Valid @RequestBody Menu menu) {
		return ResponseEntity.ok(menu);
	}
}
