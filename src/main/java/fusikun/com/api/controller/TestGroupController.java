package fusikun.com.api.controller;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoREQ.TestGroupRequest;
import fusikun.com.api.dtoRES.TestGroupResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.TestGroupService;
import fusikun.com.api.validator.TestGroupDataValidate;

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
public class TestGroupController {

    @Autowired
    TestGroupDataValidate dataValidate;

    @Autowired
    TestGroupService service;

    // Create
    @PostMapping("/test-groups")
    public ResponseEntity<Object> handleCreateTestGroup(
            @Valid @RequestBody TestGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        dataValidate.validate(req);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service._createTestGroup(req));
    }

    // FETCH TEST_GROUP:
    @GetMapping("/owners/{ownerId}/test-group")
    public ResponseEntity<List<TestGroupResponse>>
    handleGetTestGroupsByOwnerId(@PathVariable UUID ownerId)
            throws NotFoundException {
        dataValidate.validateOwnerNotExistByOwnerId(ownerId);
        return ResponseEntity.ok(service._getTestGroupsByOwnerId(ownerId));
    }

    @GetMapping("/test-group")
    public ResponseEntity<ObjectsManagementList>
    handleFetchTestGroupsByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        return ResponseEntity
                .ok(service._getTestGroupManagement(filters, limit, page, sortBy, order));
    }

    // UPDATE
    @PutMapping("/test-group/{id}")
    public ResponseEntity<TestGroupResponse> handleUpdateTestGroupById(
            @PathVariable UUID id,
            @Valid @RequestBody TestGroupRequest req
    ) throws Ex_MethodArgumentNotValidException, NotFoundException {
        req.setId(id);
        dataValidate.validateTestGroupNotExistById(id);
        dataValidate.validate(req);
        return ResponseEntity.ok(service._updateTestGroupById(req, id));
    }

    // DELETE
    @DeleteMapping("/test-group/{id}")
    public ResponseEntity<Object> handleDeleteTestGroupById(@PathVariable UUID id)
            throws NotFoundException {
        dataValidate.validateTestGroupNotExistById(id);
        return ResponseEntity.ok(service._deleteById(id));
    }
}