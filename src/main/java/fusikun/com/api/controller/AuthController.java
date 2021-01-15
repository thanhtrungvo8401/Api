package fusikun.com.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.AuthRequest;
import fusikun.com.api.dto.AuthResponse;
import fusikun.com.api.model.app.Auth;
import fusikun.com.api.service.AuthService;
import fusikun.com.api.validator.AuthDataValidate;
import javassist.NotFoundException;

@RestController
public class AuthController {
	@Autowired
	AuthService authService;

	@Autowired
	AuthDataValidate authDataValidate;

	@PatchMapping("/auths/{id}/update")
	public ResponseEntity<Object> handleUpdateAuthById(@Valid @RequestBody AuthRequest authRequest,
			@PathVariable UUID id) throws NotFoundException {
		// CUSTOM VALIDATE:
		authRequest.setId(id);
		authDataValidate.validateExistById(authRequest.getId());
		// SAVE ROLE:
		Auth oldAuth = authService.findById(authRequest.getId());
		oldAuth.setIsActive(authRequest.getIsActive());
		Auth savedAuth = authService.save(oldAuth);
		return ResponseEntity.ok(new AuthResponse(savedAuth));
	}
}
