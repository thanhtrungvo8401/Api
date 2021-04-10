package fusikun.com.api.controller;

import fusikun.com.api.service.AuthenticateService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    @PostMapping("/authenticate/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        try {
            return ResponseEntity
                    .ok(authenticateService._validate_GenerateToken(authenticationRequest));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @PostMapping("/authenticate/logout")
    public ResponseEntity<String> handleLogout() {
        try {
            return ResponseEntity.ok(authenticateService._logout());
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }
}
