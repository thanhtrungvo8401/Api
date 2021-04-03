package fusikun.com.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import fusikun.com.api.dto.ObjectsManagementList;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.specificationSearch.*;
import fusikun.com.api.utils.SortHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fusikun.com.api.dto.VocaRequest;
import fusikun.com.api.dto.VocaResponse;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.model.study.Voca;
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
        // Validate:
        vocaDataValidate.validate(vocaRequest);
        vocaDataValidate.validateExistSetVocaById(vocaRequest.getSetId());
        vocaDataValidate.validateOverMaxVoca(vocaRequest);
        // Save:
        // we are not checking unique for CODE
        Voca voca = vocaRequest.getVocaObject();
        vocaService.create(voca);
        // Return
        return ResponseEntity.status(HttpStatus.CREATED).body(new VocaResponse(voca));
    }

    // UPDATE
    @PutMapping("/vocas/{id}")
    public ResponseEntity<Object> handleUpdateVoca(@Valid @RequestBody VocaRequest vocaRequest, @PathVariable UUID id)
            throws NotFoundException {
        // Validate:
        vocaRequest.setId(id);
        vocaDataValidate.validateExistVocaById(vocaRequest.getId());
        vocaDataValidate.validate(vocaRequest);
        // Update
        Voca oldVoca = vocaService.findById(vocaRequest.getId());
        oldVoca.setNote(vocaRequest.getNote());
        oldVoca.setMeaning(vocaRequest.getMeaning());
        oldVoca.setSentence(vocaRequest.getSentence());
        oldVoca.setVoca(vocaRequest.getVoca());

        vocaService.save(oldVoca);
        return ResponseEntity.ok(new VocaResponse(oldVoca));
    }

    // DELETE
    @DeleteMapping("/vocas/{id}")
    public ResponseEntity<Object> handleDeleteVoca(@PathVariable UUID id) throws NotFoundException {
        // validate
        vocaDataValidate.validateExistVocaById(id);
        Voca voca = vocaService.findById(id);
        // delete
        SetVoca setVoca = voca.getSetVoca();
        vocaService.delete(voca);
        setVoca.decreaseVoca();
        setVocaService.save(setVoca);
        return ResponseEntity.ok(new VocaResponse(voca));
    }

    // FETCH VOCAS
    @GetMapping("/set-vocas/{id}/vocas")
    public ResponseEntity<Object> handleGetVocasInSetVoca(@PathVariable UUID id) throws NotFoundException {
        // Validate:
        vocaDataValidate.validateExistSetVocaById(id);
        // Fetch
        Specification_Voca specification = getVocaSpecificationFromSetVocasId(id);
        List<Voca> vocas = vocaService.findAll(specification);
        // Return
        List<VocaResponse> vocaResponses = vocas.stream().map(VocaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(vocaResponses);
    }

    @GetMapping("/vocas")
    public ResponseEntity<Object> handleGetVocasByFilter(
            @RequestParam(name = "filters", required = false) String filters,
            @RequestParam(name = "limit", required = false) String limit,
            @RequestParam(name = "page", required = false) String page,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "order", required = false) String order
    ) {
        Specification_Voca vocaSpecification =
                new SearchHelpers_Vocas(new Specification_Voca(), filters)
                        .getSpecification(Arrays.asList(
                                "id," + ApiDataType.UUID_TYPE,
                                "createdDate," + ApiDataType.DATE_TYPE,
                                "note," + ApiDataType.STRING_TYPE,
                                "meaning," + ApiDataType.STRING_TYPE,
                                "sentence," + ApiDataType.STRING_TYPE,
                                "updatedDate," + ApiDataType.DATE_TYPE,
                                "voca," + ApiDataType.STRING_TYPE,
                                "setVoca.id," + ApiDataType.UUID_TYPE,
                                "setVoca.setName," + ApiDataType.STRING_TYPE
                        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        List<Voca> vocas = vocaService.findAll(vocaSpecification, pageable);
        Long total = vocaService.count(vocaSpecification);
        List<VocaResponse> vocaResponses = vocas.stream()
                .map(VocaResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new VocasManagement(vocaResponses, total));
    }

    private Specification_Voca getVocaSpecificationFromSetVocasId(UUID id) {
        SetVoca setVoca = setVocaService.findById(id);
        Specification_Voca specification = new Specification_Voca();
        specification.add(new _SearchCriteria("setVoca", SearchOperator.EQUAL, setVoca));
        return specification;
    }

    private class VocasManagement extends ObjectsManagementList<VocaResponse> {
        public VocasManagement(List<VocaResponse> list, Long total) {
            super(list, total);
        }
    }
}
