package fusikun.com.api.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fusikun.com.api.dto.VocaRequest;
import fusikun.com.api.dto.VocaResponse;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.model.study.Voca;
import fusikun.com.api.service.SetVocaService;
import fusikun.com.api.service.VocaService;
import fusikun.com.api.specificationSearch.Specification_Voca;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.specificationSearch._SearchOperator;
import fusikun.com.api.validator.VocaDataValidate;
import javassist.NotFoundException;

@RestController
public class VocaController {
	@Autowired
	VocaDataValidate vocaDataValidate;

	@Autowired
	VocaService vocaService;

	@Autowired
	SetVocaService setVocaService;

	// CREATE
	@PostMapping("/api/common/v1/vocas")
	public ResponseEntity<Object> handleCreateVoca(@Valid @RequestBody VocaRequest vocaRequest)
			throws NotFoundException {
		// Validate:
		vocaDataValidate.validate(vocaRequest);
		vocaDataValidate.validateExistSetVocaById(vocaRequest.getSetId());
		vocaDataValidate.validateOverMaxVoca(vocaRequest);
		// Save:
		Voca voca = vocaRequest.getVocaObject();
		SetVoca setVoca = setVocaService.findById(vocaRequest.getSetId());
		setVoca.increaseVoca();
		vocaService.save(voca);
		setVocaService.save(setVoca);
		// Return
		return ResponseEntity.status(HttpStatus.CREATED).body(new VocaResponse(voca));
	}

	// UPDATE
	@PutMapping("/api/common/v1/vocas/{id}")
	public ResponseEntity<Object> handleUpdateVoca(@Valid @RequestBody VocaRequest vocaRequest, @PathVariable UUID id)
			throws NotFoundException {
		// Validate:
		vocaRequest.setId(id);
		vocaDataValidate.validateExistVocaById(vocaRequest.getId());
		vocaDataValidate.validateExistSetVocaById(vocaRequest.getSetId());
		vocaDataValidate.validate(vocaRequest);
		// Update
		Voca oldVoca = vocaService.findById(vocaRequest.getId());
		if (!vocaRequest.getSetId().equals(oldVoca.getSetVoca().getId())) {
			// validate
			vocaDataValidate.validateOverMaxVoca(vocaRequest);
			// update
			SetVoca setVocaRemove = oldVoca.getSetVoca();
			setVocaRemove.decreaseVoca();
			SetVoca setVocaAdd = setVocaService.findById(vocaRequest.getSetId());
			setVocaAdd.increaseVoca();
			setVocaService.save(setVocaAdd);
			setVocaService.save(setVocaRemove);
		}

		oldVoca.setNote(vocaRequest.getNote());
		oldVoca.setMeaning(vocaRequest.getMeaning());
		oldVoca.setSentence(vocaRequest.getSentence());
		oldVoca.setVoca(vocaRequest.getVoca());

		SetVoca setVoca = new SetVoca();
		setVoca.setId(vocaRequest.getSetId());

		oldVoca.setSetVoca(setVoca);
		vocaService.save(oldVoca);
		return ResponseEntity.ok(new VocaResponse(oldVoca));
	}

	// DELETE
	@DeleteMapping("/api/common/v1/vocas/{id}")
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
	@GetMapping("/api/common/v1/set-vocas/{id}/vocas")
	public ResponseEntity<Object> handleGetVocasInSetVoca(@PathVariable UUID id) throws NotFoundException {
		// Validate:
		vocaDataValidate.validateExistSetVocaById(id);
		// Fetch
		Specification_Voca specification = getVocaSpecificationFromSetVocasId(id);
		Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "createdDate");
		List<Voca> vocas = vocaService.findAll(specification, pageable);
		// Return
		List<VocaResponse> vocaResponses = vocas.stream().map(voca -> new VocaResponse(voca))
				.collect(Collectors.toList());
		return ResponseEntity.ok(vocaResponses);
	}

	private Specification_Voca getVocaSpecificationFromSetVocasId(UUID id) {
		SetVoca setVoca = setVocaService.findById(id);
		Specification_Voca specification = new Specification_Voca();
		specification.add(new _SearchCriteria("setVoca", _SearchOperator.EQUAL, setVoca));
		return specification;
	}
}
