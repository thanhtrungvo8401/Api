package fusikun.com.api.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.utils.Constant;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(VocaController.class);

    // CREATE
    @PostMapping("/vocas")
    public ResponseEntity<Object> handleCreateVoca(@Valid @RequestBody VocaRequest vocaRequest)
            throws NotFoundException {
        try {
            vocaDataValidate.validate(vocaRequest);
            vocaDataValidate.validateExistSetVocaById(vocaRequest.getSetId());
            vocaDataValidate.validateOverMaxVoca(vocaRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(vocaService._createVoca(vocaRequest));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // UPDATE
    @PutMapping("/vocas/{id}")
    public ResponseEntity<Object> handleUpdateVoca(
            @Valid @RequestBody VocaRequest vocaRequest,
            @PathVariable UUID id
    ) throws NotFoundException {
        try {
            vocaRequest.setId(id);
            vocaDataValidate.validateExistVocaById(vocaRequest.getId());
            vocaDataValidate.validate(vocaRequest);
            return ResponseEntity
                    .ok(vocaService._updateVocaById(vocaRequest, id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // DELETE
    @DeleteMapping("/vocas/{id}")
    public ResponseEntity<Object> handleDeleteVoca(@PathVariable UUID id) throws NotFoundException {
        try {
            vocaDataValidate.validateExistVocaById(id);
            return ResponseEntity
                    .ok(vocaService._deleteById(id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // FETCH VOCAS
    @GetMapping("/set-vocas/{id}/vocas")
    public ResponseEntity<List<VocaResponse>> handleGetVocasInSetVoca(@PathVariable UUID id) throws NotFoundException {
        try {
            vocaDataValidate.validateExistSetVocaById(id);
            return ResponseEntity.ok(vocaService._getVocasBySetId(id));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @GetMapping("/vocas")
    public ResponseEntity<ObjectsManagementList> handleGetVocasByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        try {
            return ResponseEntity
                    .ok(vocaService._getVocasManagement(filters, limit, page, sortBy, order));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    // FETCH VOCAS BY RAND:
    @GetMapping("/vocas/random/{level}")
    public  ResponseEntity<List<VocaResponse>> getRandomVocaByLevel(@PathVariable String level) {
        try {
            return ResponseEntity.ok(vocaService._getRandomVocaByLevel(level, Constant.RANDOM_LIMIT));
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }

    @GetMapping("/test-vocas/vocas")
    public ResponseEntity<List<VocaResponse>> getVocasBaseOnTestVocas() {
        try {
            return ResponseEntity.ok(vocaService._getVocasBaseOnTestVocas());
        } catch (Exception ex) {
            String stackTrace = ExceptionUtils.getStackTrace(ex);
            logger.error(stackTrace);
            throw ex;
        }
    }


}
