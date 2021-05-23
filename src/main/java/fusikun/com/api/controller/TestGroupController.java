package fusikun.com.api.controller;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoREQ.TestGroupRequest;
import fusikun.com.api.dtoRES.TestGroupResponse;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.service.TestGroupService;
import fusikun.com.api.validator.TestGroupDataValidate;

import javassist.NotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/common/v1")
public class TestGroupController {

    @Autowired
    TestGroupDataValidate dataValidate;

    @Autowired
    TestGroupService service;

    Logger logger = LoggerFactory.getLogger(TestGroupController.class);

    // Create
    @PostMapping("/test-groups")
    public ResponseEntity<Object> handleCreateTestGroup(
            @Valid @RequestBody TestGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        try {
            dataValidate.validate(req);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(service._createTestGroup(req));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // FETCH TEST_GROUP:
    @GetMapping("/owners/{ownerId}/test-group")
    public ResponseEntity<TestGroupResponse>
    handleGetTestGroupsByOwnerId(@PathVariable UUID ownerId)
            throws NotFoundException {
        try {
            dataValidate.validateOwnerNotExistByOwnerId(ownerId);
            return ResponseEntity.ok(service._getTestGroupsByOwnerId(ownerId));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
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
        try {
            return ResponseEntity
                    .ok(service._getTestGroupManagement(filters, limit, page, sortBy, order));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // UPDATE
    @PutMapping("/test-group/{id}")
    public ResponseEntity<TestGroupResponse> handleUpdateTestGroupById(
            @PathVariable UUID id,
            @Valid @RequestBody TestGroupRequest req
    ) throws Ex_MethodArgumentNotValidException, NotFoundException {
        try {
            req.setId(id);
            dataValidate.validateTestGroupNotExistById(id);
            dataValidate.validate(req);
            return ResponseEntity.ok(service._updateTestGroupById(req, id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // DELETE
    @DeleteMapping("/test-group/{id}")
    public ResponseEntity<Object> handleDeleteTestGroupById(@PathVariable UUID id)
            throws NotFoundException {
        try {
            dataValidate.validateTestGroupNotExistById(id);
            return ResponseEntity.ok(service._deleteById(id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }
}