package fusikun.com.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import fusikun.com.api.dto.ObjectsManagementList;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.specificationSearch.SearchHelpers_SetVocas;
import fusikun.com.api.utils.SortHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dto.SetVocaRequest;
import fusikun.com.api.dto.SetVocaResponse;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.service.SetVocaService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.specificationSearch.Specification_SetVoca;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.validator.SetVocaDataValidate;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/common/v1")
public class SetVocaController {

    @Autowired
    SetVocaDataValidate setVocaDataValidate;

    @Autowired
    SetVocaService setVocaService;

    @Autowired
    UserService userService;

    // CREATE
    @PostMapping("/set-vocas")
    public ResponseEntity<Object> handleCreateSetVocas(@Valid @RequestBody SetVocaRequest setVocaRequest)
            throws NotFoundException {
        // Validate:
        setVocaDataValidate.validate(setVocaRequest);
        setVocaDataValidate.validateAuthorNotExistById(setVocaRequest.getAuthorId());
        setVocaDataValidate.validateSetVocasOverRange(setVocaRequest.getAuthorId(),
                getSetVocaSpecification(setVocaRequest.getAuthorId()));
        // Save data:
        SetVoca setVoca = setVocaRequest.getSetVoca();
        setVocaService.save(setVoca);
        // return:
        return ResponseEntity.status(HttpStatus.CREATED).body(new SetVocaResponse(setVoca));
    }

    // UPDATE:
    @PutMapping("/set-vocas/{setVocaId}")
    public ResponseEntity<Object> handleUpdateSetVocasById(@PathVariable UUID setVocaId,
                                                           @Valid @RequestBody SetVocaRequest setVocaRequest) throws NotFoundException {
        // Validate:
        setVocaRequest.setId(setVocaId);
        setVocaDataValidate.validateSetVocaIdNotExist(setVocaRequest.getId());
        setVocaDataValidate.validateAuthorNotExistById(setVocaRequest.getAuthorId());
        setVocaDataValidate.validate(setVocaRequest);
        // Update:
        SetVoca oldSetVoca = setVocaService.findById(setVocaId);
        SetVoca setVoca = setVocaRequest.getSetVoca();
//        oldSetVoca.setAuthor(setVoca.getAuthor()); // ----validate over-range here ----
        oldSetVoca.setSetName(setVoca.getSetName());
        SetVoca savedSetVoca = setVocaService.save(oldSetVoca);
        // return:
        return ResponseEntity.ok(new SetVocaResponse(savedSetVoca));
    }

    // FETCH SET-VOCAS:
    @GetMapping("/users/{authorId}/set-vocas")
    public ResponseEntity<Object> handleGetSetVocasCreatedByAuthor(@PathVariable UUID authorId)
            throws NotFoundException {
        // validate:
        setVocaDataValidate.validateAuthorNotExistById(authorId);
        // fetch data:
        Specification_SetVoca specification = getSetVocaSpecification(authorId);
        List<SetVoca> setVocas = setVocaService.findAll(specification);
        List<SetVocaResponse> setVocaResponses = setVocas.stream().map(SetVocaResponse::new)
                .collect(Collectors.toList());
        // return
        return ResponseEntity.ok(setVocaResponses);
    }

    @GetMapping("/set-vocas/{id}")
    public ResponseEntity<Object> handleGetSetVocasById(@PathVariable UUID id) throws NotFoundException {
        // Validate
        setVocaDataValidate.validateSetVocaIdNotExist(id);
        // get data
        SetVoca setVoca = setVocaService.findById(id);
        // return
        return ResponseEntity.ok(new SetVocaResponse(setVoca));
    }

    @GetMapping("centers/roles/set-vocas")
    public ResponseEntity<SetVocasManagement> handleGetSetVocasByCenterIdAndFilters(
            @RequestParam(name = "centerId") UUID centerId,
            @RequestParam(name = "roleName") String roleName,
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        List<UUID> userIds = userService.getUserIdsBaseOnCenterIdAndRoleName(centerId, roleName);
        Specification_SetVoca spec = new Specification_SetVoca();
        if (!userIds.isEmpty()) {
            spec.add(new _SearchCriteria("author", SearchOperator.IN, userIds, "id",
                    ApiDataType.UUID_TYPE));
        }

        Specification_SetVoca specification = new SearchHelpers_SetVocas(spec, filters)
                .getSpecification(Arrays.asList(
                        "id," + ApiDataType.UUID_TYPE,
                        "maxVoca," + ApiDataType.INTEGER_TYPE,
                        "createdDate," + ApiDataType.DATE_TYPE,
                        "updatedDate," + ApiDataType.DATE_TYPE,
                        "setName," + ApiDataType.STRING_TYPE,
                        "author.id," + ApiDataType.UUID_TYPE,
                        "totalVocas," + ApiDataType.INTEGER_TYPE
                ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        Long total = setVocaService.count(specification);
        List<SetVoca> setVocas = setVocaService.findAll(specification, pageable);
        List<SetVocaResponse> setVocasResponses = setVocas.stream()
                .map(SetVocaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new SetVocasManagement(setVocasResponses, total));
    }

    // DELETE
    @DeleteMapping("/set-vocas/{id}")
    public ResponseEntity<Object> handleDeleteSetVoca(@PathVariable UUID id) throws NotFoundException {
        // validate:
        setVocaDataValidate.validateSetVocaIdNotExist(id);
        // delete:
        SetVoca setVoca = setVocaService.findById(id);
        setVocaService.delete(setVoca);
        // return
        return ResponseEntity.ok(new SetVocaResponse(setVoca));
    }

    private Specification_SetVoca getSetVocaSpecification(UUID authorId) {
        Specification_SetVoca specification = new Specification_SetVoca();
        User author = userService.findById(authorId);
        specification.add(new _SearchCriteria("author", SearchOperator.EQUAL, author));
        return specification;
    }

    private class SetVocasManagement extends ObjectsManagementList<SetVocaResponse> {
        public SetVocasManagement(List<SetVocaResponse> list, Long total) {
            super(list, total);
        }
    }
}