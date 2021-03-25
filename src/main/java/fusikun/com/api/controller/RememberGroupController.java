package fusikun.com.api.controller;

import fusikun.com.api.dto.ObjectsManagementList;
import fusikun.com.api.dto.RememberGroupRequest;
import fusikun.com.api.dto.RememberGroupResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.exceptionHandlers.Ex_MethodArgumentNotValidException;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.RememberGroup;
import fusikun.com.api.service.RememberGroupService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.specificationSearch.SearchHelpers_RememberGroup;
import fusikun.com.api.specificationSearch.Specification_RememberGroup;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.utils.SortHelper;
import fusikun.com.api.validator.RememberGroupDataValidate;
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
public class RememberGroupController {

    @Autowired
    RememberGroupDataValidate remGroupDataValidate;

    @Autowired
    RememberGroupService remService;

    @Autowired
    UserService userService;


    // CREATE
    @PostMapping("/remember-groups")
    public ResponseEntity<Object> handleCreateRememberGroup(
            @Valid
            @RequestBody RememberGroupRequest req)
            throws Ex_MethodArgumentNotValidException {
        remGroupDataValidate.validate(req);
        RememberGroup remGroup = req.getRemembersGroup();
        remService.save(remGroup);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RememberGroupResponse(remGroup));
    }

    // FETCH REMEMBER_GROUP:
    @GetMapping("/owners/{ownerId}/remember-groups")
    public ResponseEntity<Object>
    handleGetAllRememberGroupByOwnerId(@PathVariable UUID ownerId)
            throws NotFoundException {
        // validate:
        remGroupDataValidate.validateOwnerNotExistByOwnerId(ownerId);
        // fetch data:
        List<RememberGroup> rememberGroups =
                remService.findAll(getRemGroupSpeciByOwner(ownerId));
        List<RememberGroupResponse> rememberGroupResponses = rememberGroups
                .stream()
                .map(RememberGroupResponse::new)
                .collect(Collectors.toList());
        // return
        return ResponseEntity.ok(rememberGroupResponses);
    }

    @GetMapping("/remember-groups")
    public ResponseEntity<Object>
    handleFetchRememberGroupsByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        Specification_RememberGroup specification =
                new SearchHelpers_RememberGroup(new Specification_RememberGroup(), filters)
                        .getSpecification(Arrays.asList(
                                "id," + ApiDataType.UUID_TYPE,
                                "vocaCodes," + ApiDataType.STRING_TYPE,
                                "createdDate," + ApiDataType.DATE_TYPE,
                                "updatedDate," + ApiDataType.DATE_TYPE,
                                "name," + ApiDataType.DATE_TYPE,
                                "owner.id," + ApiDataType.UUID_TYPE
                        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        List<RememberGroup> remGroups = remService.findAll(specification, pageable);
        Long total = remService.count(specification);
        List<RememberGroupResponse> remGroupsResponses = remGroups
                .stream()
                .map(RememberGroupResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new RemGroupManagement(remGroupsResponses, total));
    }

    // UPDATE
    @PutMapping("/remember-groups/{id}")
    public ResponseEntity<Object> handleUpdateRemGroupById(
            @PathVariable UUID id,
            @Valid @RequestBody RememberGroupRequest req
    ) throws NotFoundException, Ex_MethodArgumentNotValidException {
        // Validate
        req.setId(id);
        remGroupDataValidate.validateRemGroupNotExistById(req.getId());
        remGroupDataValidate.validate(req);
        // Update
        RememberGroup oldRemGroup = remService.findById(req.getId());
        RememberGroup remGroup = req.getRemembersGroup();
        // ---
        oldRemGroup.setName(remGroup.getName());
        oldRemGroup.setVocaCodes(remGroup.getVocaCodes());
        oldRemGroup.setActiveCodes(remGroup.getActiveCodes());
//        oldRemGroup.setOwner(remGroup.getOwner());
        RememberGroup savedRememberGroup = remService.save(oldRemGroup);
        return ResponseEntity.ok(
                new RememberGroupResponse(savedRememberGroup)
        );
    }

    // DELETE
    @DeleteMapping("/remember-groups/{id}")
    public ResponseEntity<Object> handleDeleteRemGroupById(@PathVariable UUID id)
            throws NotFoundException {
        // validate
        remGroupDataValidate.validateRemGroupNotExistById(id);
        RememberGroup rememberGroup = remService.findById(id);
        // delete
        remService.delete(rememberGroup);
        return ResponseEntity.ok(new RememberGroupResponse(rememberGroup));
    }

    private Specification_RememberGroup getRemGroupSpeciByOwner(UUID ownerId) {
        Specification_RememberGroup specification = new Specification_RememberGroup();
        User owner = userService.findById(ownerId);
        specification.add(new _SearchCriteria("owner", SearchOperator.EQUAL, owner));
        return specification;
    }

    private class RemGroupManagement extends ObjectsManagementList<RememberGroupResponse> {
        public RemGroupManagement(List<RememberGroupResponse> list, Long total) {
            super(list, total);
        }
    }
}
