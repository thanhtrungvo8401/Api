package fusikun.com.api.controller;

import java.util.UUID;

import javax.validation.Valid;

import fusikun.com.api.dtoRES.AuthResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    // JWT with ROLE, ROLE = ADMIN or STUDENT => not get ROLE and Authorization
    // ROLE with STUDENT => /api/common/** => can access without checkout permission */
    @PutMapping("/auths/{id}")
    public ResponseEntity<AuthResponse> handleUpdateAuthById(
            @Valid @RequestBody AuthRequest authRequest,
            @PathVariable UUID id) throws NotFoundException {
        try {
            authRequest.setId(id);
            authDataValidate.validateExistById(authRequest.getId());
            return ResponseEntity.ok(authService._updateAuthById(authRequest, id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }
}
