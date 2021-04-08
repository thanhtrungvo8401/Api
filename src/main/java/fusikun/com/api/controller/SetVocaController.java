package fusikun.com.api.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dtoREQ.SetVocaRequest;
import fusikun.com.api.dtoRES.SetVocaResponse;
import fusikun.com.api.model.app.User;
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
    public ResponseEntity<SetVocaResponse> handleCreateSetVocas(
            @Valid @RequestBody SetVocaRequest req
    ) throws NotFoundException {
        setVocaDataValidate.validate(req);
        setVocaDataValidate.validateAuthorNotExistById(req.getAuthorId());
        setVocaDataValidate.validateSetVocasOverRange(req.getAuthorId(),
                getSetVocaSpecification(req.getAuthorId()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(setVocaService._createSetVocas(req));
    }

    // UPDATE:
    @PutMapping("/set-vocas/{setVocaId}")
    public ResponseEntity<SetVocaResponse> handleUpdateSetVocasById(
            @PathVariable UUID setVocaId,
            @Valid @RequestBody SetVocaRequest req)
            throws NotFoundException {
        req.setId(setVocaId);
        setVocaDataValidate.validateSetVocaIdNotExist(req.getId());
        setVocaDataValidate.validateAuthorNotExistById(req.getAuthorId());
        setVocaDataValidate.validate(req);
        return ResponseEntity
                .ok(setVocaService._updateSetVocasById(req, setVocaId));
    }

    // FETCH SET-VOCAS:
    @GetMapping("/users/{authorId}/set-vocas")
    public ResponseEntity<List<SetVocaResponse>> handleGetSetVocasCreatedByAuthor
    (@PathVariable UUID authorId)
            throws NotFoundException {
        setVocaDataValidate.validateAuthorNotExistById(authorId);
        return ResponseEntity.ok(setVocaService._getSetVocasByAuthorId(authorId));
    }

    @GetMapping("/set-vocas/{id}")
    public ResponseEntity<SetVocaResponse> handleGetSetVocasById
            (@PathVariable UUID id)
            throws NotFoundException {
        setVocaDataValidate.validateSetVocaIdNotExist(id);
        return ResponseEntity
                .ok(setVocaService._getSetVocasById(id));
    }

    @GetMapping("centers/roles/set-vocas")
    public ResponseEntity<ObjectsManagementList>
    handleGetSetVocasByCenterIdAndFilters(
            @RequestParam(name = "centerId") UUID centerId,
            @RequestParam(name = "roleName") String roleName,
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        return ResponseEntity.ok(
                setVocaService._getSetVocasManagementByCenterIdAndRoleName(
                        centerId, roleName, filters, limit, page, sortBy, order
                )
        );
    }

    // DELETE
    @DeleteMapping("/set-vocas/{id}")
    public ResponseEntity<Object> handleDeleteSetVoca(@PathVariable UUID id) throws NotFoundException {
        setVocaDataValidate.validateSetVocaIdNotExist(id);
        return ResponseEntity.ok(setVocaService._deleteById(id));
    }

    private Specification_SetVoca getSetVocaSpecification(UUID authorId) {
        Specification_SetVoca specification = new Specification_SetVoca();
        User author = userService.findById(authorId);
        specification.add(new _SearchCriteria("author", SearchOperator.EQUAL, author));
        return specification;
    }
}