package fusikun.com.api.controller;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoREQ.RememberGroupRequest;
import fusikun.com.api.dtoRES.RememberGroupResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.RememberGroupService;
import fusikun.com.api.validator.RememberGroupDataValidate;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/v1")
public class RememberGroupController {

    @Autowired
    RememberGroupDataValidate remGroupDataValidate;

    @Autowired
    RememberGroupService remService;

    // CREATE
    @PostMapping("/remember-groups")
    public ResponseEntity<RememberGroupResponse> handleCreateRememberGroup(
            @Valid
            @RequestBody RememberGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        remGroupDataValidate.validate(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(remService._createRememberGroup(req));
    }

    // FETCH REMEMBER_GROUP:
    @GetMapping("/owners/{ownerId}/remember-groups")
    public ResponseEntity<List<RememberGroupResponse>>
    handleGetAllRememberGroupByOwnerId(@PathVariable UUID ownerId)
            throws NotFoundException {
        remGroupDataValidate.validateOwnerNotExistByOwnerId(ownerId);
        return ResponseEntity.ok(remService._findAllByOwnerId(ownerId));
    }

    @GetMapping("/remember-groups")
    public ResponseEntity<ObjectsManagementList>
    handleFetchRememberGroupsByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        return ResponseEntity.ok(
                remService._findManagementByFilters(filters, limit, page, sortBy, order));
    }

    // UPDATE
    @PutMapping("/remember-groups/{id}")
    public ResponseEntity<RememberGroupResponse> handleUpdateRemGroupById(
            @PathVariable UUID id,
            @Valid @RequestBody RememberGroupRequest req
    ) throws NotFoundException, Ex_MethodArgumentNotValidException {
        remGroupDataValidate.validateRemGroupNotExistById(id);
        remGroupDataValidate.validate(req);
        return ResponseEntity.ok(remService._updateById(req, id));
    }

    // DELETE
    @DeleteMapping("/remember-groups/{id}")
    public ResponseEntity<RememberGroupResponse> handleDeleteRemGroupById(@PathVariable UUID id)
            throws NotFoundException {
        remGroupDataValidate.validateRemGroupNotExistById(id);
        return ResponseEntity.ok(remService._deleteById(id));
    }

}
