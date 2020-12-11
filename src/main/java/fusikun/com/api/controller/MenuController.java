package fusikun.com.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MenuController {
	@GetMapping("/menus")
	public ResponseEntity<Object> getMenus() {
		return ResponseEntity.ok("List MENU");
	}
}
