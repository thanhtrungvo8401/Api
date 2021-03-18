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

import fusikun.com.api.dto.SetVocaRequest;
import fusikun.com.api.dto.SetVocaResponse;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.service.SetVocaService;
import fusikun.com.api.service.UserService;
import fusikun.com.api.specificationSearch.Specification_SetVoca;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.specificationSearch._SearchOperator;
import fusikun.com.api.validator.SetVocaDataValidate;
import javassist.NotFoundException;

@RestController
public class SetVocaController {

	@Autowired
	SetVocaDataValidate setVocaDataValidate;

	@Autowired
	SetVocaService setVocaService;

	@Autowired
	UserService userService;

	// CREATE
	@PostMapping("/api/common/v1/set-vocas")
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
	@PutMapping("/api/common/v1/set-vocas/{setVocaId}")
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
		// ----validate over-range here ----
		oldSetVoca.setAuthor(setVoca.getAuthor());
		oldSetVoca.setSetName(setVoca.getSetName());
		SetVoca savedSetVoca = setVocaService.save(oldSetVoca);
		// return:
		return ResponseEntity.ok(new SetVocaResponse(savedSetVoca));
	}

	// FETCH SET-VOCAS:
	@GetMapping("/api/common/v1/users/{authorId}/set-vocas")
	public ResponseEntity<Object> handleGetSetVocasCreatedByAuthor(@PathVariable UUID authorId)
			throws NotFoundException {
		// validate:
		setVocaDataValidate.validateAuthorNotExistById(authorId);
		// fetch data:
		Specification_SetVoca specification = getSetVocaSpecification(authorId);
		Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "createdDate");
		List<SetVoca> setVocas = setVocaService.findAll(specification, pageable);
		List<SetVocaResponse> setVocaResponses = setVocas.stream().map(setVoca -> new SetVocaResponse(setVoca))
				.collect(Collectors.toList());
		// return
		return ResponseEntity.ok(setVocaResponses);
	}

	@GetMapping("/api/common/v1/set-vocas/{id}")
	public ResponseEntity<Object> handleGetSetVocasById(@PathVariable UUID id) throws NotFoundException {
		// Validate
		setVocaDataValidate.validateSetVocaIdNotExist(id);
		// get data
		SetVoca setVoca = setVocaService.findById(id);
		// return
		return ResponseEntity.ok(new SetVocaResponse(setVoca));
	}

	// DELETE
	@DeleteMapping("/api/common/v1/set-vocas/{id}")
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
		specification.add(new _SearchCriteria("author", _SearchOperator.EQUAL, author));
		return specification;
	}
}
