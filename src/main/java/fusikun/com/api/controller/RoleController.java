package fusikun.com.api.controller;

import java.util.UUID;
import javax.validation.Valid;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dtoREQ.RoleRequest;
import fusikun.com.api.dtoRES.RoleResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.RoleService;
import fusikun.com.api.validator.RoleDataValidate;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired
    RoleDataValidate roleDataValidate;

    @Autowired
    RoleService roleService;

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @GetMapping("/roles")
    public ResponseEntity<ObjectsManagementList> getAllRoles() {
        return ResponseEntity
                .ok(roleService._findRoleManagements());
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> handleCreateRole(@Valid @RequestBody RoleRequest roleRequest)
            throws Ex_MethodArgumentNotValidException {
        try {
            roleDataValidate.validate(roleRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(roleService._createRole(roleRequest));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable UUID id) throws NotFoundException {
        try {
            roleDataValidate.validateExistById(id);
            return ResponseEntity
                    .ok(roleService._getRoleById(id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> handleUpdateRoleById(
            @Valid @RequestBody RoleRequest roleReq,
            @PathVariable UUID id)
            throws Ex_MethodArgumentNotValidException, NotFoundException {
        try {
            roleReq.setId(id);
            roleDataValidate.validateExistById(roleReq.getId());
            roleDataValidate.validate(roleReq);
            return ResponseEntity
                    .ok(roleService._updateRoleById(roleReq, id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> handleDeleteRoleById(@PathVariable UUID id)
            throws NotFoundException {
        try {
            roleDataValidate.validateExistById(id);
            roleDataValidate.validateNeverDeleteRole(id);
            roleDataValidate.validateRoleIsUsedByUser(id);
            return ResponseEntity
                    .ok(roleService._deleteRoleById(id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }
}
