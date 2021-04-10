package fusikun.com.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import fusikun.com.api.dtoRES.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dtoREQ.AuthRequest;
import fusikun.com.api.service.AuthService;
import fusikun.com.api.validator.AuthDataValidate;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    AuthDataValidate authDataValidate;

    // JWT with ROLE, ROLE = ADMIN or STUDENT => not get ROLE and Authorization
    // ROLE with STUDENT => /api/common/** => can access without checkout permission */
    @PutMapping("/auths/{id}")
    public ResponseEntity<AuthResponse> handleUpdateAuthById(
            @Valid @RequestBody AuthRequest authRequest,
            @PathVariable UUID id) throws NotFoundException {
        authRequest.setId(id);
        authDataValidate.validateExistById(authRequest.getId());
        return ResponseEntity.ok(authService._updateAuthById(authRequest, id));
    }
}
