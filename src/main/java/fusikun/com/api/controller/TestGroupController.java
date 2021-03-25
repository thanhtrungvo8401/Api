package fusikun.com.api.controller;

import fusikun.com.api.dto.ObjectsManagementList;
import fusikun.com.api.dto.TestGroupRequest;
import fusikun.com.api.dto.TestGroupResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.TestGroup;
import fusikun.com.api.service.TestGroupService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.specificationSearch.SearchHelpers_TestGroup;
import fusikun.com.api.specificationSearch.Specification_TestGroup;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.utils.SortHelper;
import fusikun.com.api.validator.TestGroupDataValidate;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/common/v1")
public class TestGroupController {

    @Autowired
    TestGroupDataValidate dataValidate;

    @Autowired
    TestGroupService service;

    @Autowired
    UserService userService;

    // Create
    @PostMapping("/test-groups")
    public ResponseEntity<Object> handleCreateTestGroup(
            @Valid
            @RequestBody TestGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        dataValidate.validate(req);
        TestGroup testGroup = req.getTestGroup();
        service.save(testGroup);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new TestGroupResponse(testGroup));
    }

    // FETCH TEST_GROUP:
    @GetMapping("/owners/{ownerId}/test-group")
    public ResponseEntity<Object>
    handleGetAllTestGroupByOwnerid(@PathVariable UUID ownerId) throws NotFoundException {
        // validate:
        dataValidate.validateOwnerNotExistByOwnerId(ownerId);
        // fetch data:
        List<TestGroup> testGroups =
                service.findAll(getTestGroupSpeciByOwner(ownerId));
        List<TestGroupResponse> testGroupResponses = testGroups
                .stream()
                .map(TestGroupResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(testGroupResponses);
    }

    @GetMapping("/test-group")
    public ResponseEntity<Object>
    handleFetchTestGroupsByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        Specification_TestGroup specification =
                new SearchHelpers_TestGroup(new Specification_TestGroup(), filters)
                        .getSpecification(Arrays.asList(
                                "id," + ApiDataType.UUID_TYPE,
                                "vocaCodes," + ApiDataType.STRING_TYPE,
                                "createdDate," + ApiDataType.DATE_TYPE,
                                "updatedDate," + ApiDataType.DATE_TYPE,
                                "name," + ApiDataType.STRING_TYPE,
                                "owner.id," + ApiDataType.UUID_TYPE
                        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        List<TestGroup> testGroups = service.findAll(specification, pageable);
        Long total = service.count(specification);
        List<TestGroupResponse> testGroupResponses = testGroups
                .stream()
                .map(TestGroupResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new TestGroupManagement(testGroupResponses, total));
    }

    // UPDATE
    @PutMapping("/test-group/{id}")
    public ResponseEntity<Object> handleUpdateTestGroupById(
            @PathVariable UUID id,
            @Valid @RequestBody TestGroupRequest req
    ) throws Ex_MethodArgumentNotValidException, NotFoundException {
        // Validate
        req.setId(id);
        dataValidate.validateTestGroupNotExistById(id);
        dataValidate.validate(req);
        // Update
        TestGroup oldTestGroup = service.findById(req.getId());
        TestGroup testGroup = req.getTestGroup();
        // ---
        oldTestGroup.setName(testGroup.getName());
        oldTestGroup.setVocaCodes(testGroup.getVocaCodes());
        oldTestGroup.setCorrects(testGroup.getCorrects());
        service.save(oldTestGroup);
        return ResponseEntity.ok(new TestGroupResponse(oldTestGroup));
    }

    // DELETE
    @DeleteMapping("/test-group/{id}")
    public ResponseEntity<Object> handleDeleteTestGroupById(
            @PathVariable UUID id
    ) throws NotFoundException {
        // validate
        dataValidate.validateTestGroupNotExistById(id);
        TestGroup testGroup = service.findById(id);
        // delete
        service.delete(testGroup);
        return ResponseEntity.ok(new TestGroupResponse(testGroup));
    }

    private Specification_TestGroup getTestGroupSpeciByOwner(UUID ownerId) {
        Specification_TestGroup specification = new Specification_TestGroup();
        User owner = userService.findById(ownerId);
        specification.add(new _SearchCriteria("owner", SearchOperator.EQUAL, owner));
        return specification;
    }

    private class TestGroupManagement extends ObjectsManagementList<TestGroupResponse> {
        public TestGroupManagement(List<TestGroupResponse> list, Long total) {
            super(list, total);
        }
    }
}