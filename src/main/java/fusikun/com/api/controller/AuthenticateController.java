package fusikun.com.api.controller;

import fusikun.com.api.service.AuthenticateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dtoREQ.JwtRequest;

@RestController
@RequestMapping("/api/common/v1")
public class AuthenticateController {
    @Autowired
    private AuthenticateService authenticateService;

    @PostMapping("/authenticate/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        return ResponseEntity
                .ok(authenticateService._validate_GenerateToken(authenticationRequest));
    }

    @PostMapping("/authenticate/logout")
    public ResponseEntity<String> handleLogout() {
        return ResponseEntity.ok(authenticateService._logout());
    }
}
