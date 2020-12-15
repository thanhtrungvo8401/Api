package fusikun.com.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
	@GetMapping("/menu-actions")
	public ResponseEntity<Object> getMenuActions() {
		return ResponseEntity.ok("List MENU");
	}
	
	@PostMapping("/menu-actions/create")
	public ResponseEntity<Object> createMenuActions() {
		return ResponseEntity.ok("SUCCESS");
	}
}
