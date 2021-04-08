package fusikun.com.api.controller;

import java.util.UUID;
import javax.validation.Valid;

import fusikun.com.api.dtoRES.ObjectsManagementList;
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

    @GetMapping("/roles")
    public ResponseEntity<ObjectsManagementList> getAllRoles() {
        return ResponseEntity
                .ok(roleService._findRoleManagements());
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> handleCreateRole(@Valid @RequestBody RoleRequest roleRequest)
            throws Ex_MethodArgumentNotValidException {
        roleDataValidate.validate(roleRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService._createRole(roleRequest));
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable UUID id) throws NotFoundException {
        roleDataValidate.validateExistById(id);
        return ResponseEntity
                .ok(roleService._getRoleById(id));
    }

    @PutMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> handleUpdateRoleById(
            @Valid @RequestBody RoleRequest roleReq,
            @PathVariable UUID id)
            throws Ex_MethodArgumentNotValidException, NotFoundException {
        roleReq.setId(id);
        roleDataValidate.validateExistById(roleReq.getId());
        roleDataValidate.validate(roleReq);
        return ResponseEntity
                .ok(roleService._updateRoleById(roleReq, id));
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<RoleResponse> handleDeleteRoleById(@PathVariable UUID id) throws NotFoundException {
        roleDataValidate.validateExistById(id);
        roleDataValidate.validateNeverDeleteRole(id);
        roleDataValidate.validateRoleIsUsedByUser(id);
        return ResponseEntity
                .ok(roleService._deleteRoleById(id));
    }
}
