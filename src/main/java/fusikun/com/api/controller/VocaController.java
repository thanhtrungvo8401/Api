package fusikun.com.api.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dtoREQ.VocaRequest;
import fusikun.com.api.dtoRES.VocaResponse;
import fusikun.com.api.service.SetVocaService;
import fusikun.com.api.service.VocaService;
import fusikun.com.api.validator.VocaDataValidate;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/common/v1")
public class VocaController {
    @Autowired
    VocaDataValidate vocaDataValidate;

    @Autowired
    VocaService vocaService;

    @Autowired
    SetVocaService setVocaService;

    // CREATE
    @PostMapping("/vocas")
    public ResponseEntity<Object> handleCreateVoca(@Valid @RequestBody VocaRequest vocaRequest)
            throws NotFoundException {
        vocaDataValidate.validate(vocaRequest);
        vocaDataValidate.validateExistSetVocaById(vocaRequest.getSetId());
        vocaDataValidate.validateOverMaxVoca(vocaRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(vocaService._createVoca(vocaRequest));
    }

    // UPDATE
    @PutMapping("/vocas/{id}")
    public ResponseEntity<Object> handleUpdateVoca(
            @Valid @RequestBody VocaRequest vocaRequest,
            @PathVariable UUID id
    ) throws NotFoundException {
        vocaRequest.setId(id);
        vocaDataValidate.validateExistVocaById(vocaRequest.getId());
        vocaDataValidate.validate(vocaRequest);
        return ResponseEntity
                .ok(vocaService._updateVocaById(vocaRequest, id));
    }

    // DELETE
    @DeleteMapping("/vocas/{id}")
    public ResponseEntity<Object> handleDeleteVoca(@PathVariable UUID id) throws NotFoundException {
        vocaDataValidate.validateExistVocaById(id);
        return ResponseEntity
                .ok(vocaService._deleteById(id));
    }

    // FETCH VOCAS
    @GetMapping("/set-vocas/{id}/vocas")
    public ResponseEntity<List<VocaResponse>> handleGetVocasInSetVoca(@PathVariable UUID id) throws NotFoundException {
        vocaDataValidate.validateExistSetVocaById(id);
        return ResponseEntity.ok(vocaService._getVocasBySetId(id));
    }

    @GetMapping("/vocas")
    public ResponseEntity<ObjectsManagementList> handleGetVocasByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        return ResponseEntity
                .ok(vocaService._getVocasManagement(filters, limit, page, sortBy, order));
    }
}
