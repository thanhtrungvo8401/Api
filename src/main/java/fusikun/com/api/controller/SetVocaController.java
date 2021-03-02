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
import fusikun.com.api.service.VocaService;
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
	VocaService vocaService;

	@Autowired
	UserService userService;

	// Create setVocas:
	@PostMapping("/set-vocas")
	public ResponseEntity<Object> handleCreateSetVocas(@Valid @RequestBody SetVocaRequest setVocaRequest)
			throws NotFoundException {
		setVocaDataValidate.validate(setVocaRequest);
		setVocaDataValidate.validateAuthorNotExistById(setVocaRequest.getAuthorId());
		setVocaDataValidate.validateSetVocasOverRange(setVocaRequest.getAuthorId(),
				getSetVocaSpecification(setVocaRequest.getAuthorId()));
		SetVoca setVoca = setVocaRequest.getSetVoca();
		setVocaService.save(setVoca);
		SetVocaResponse setVocaResponse = new SetVocaResponse(setVoca);
		return ResponseEntity.status(HttpStatus.CREATED).body(setVocaResponse);
	}

	// Update SetVocas by Id:
	@PutMapping("/set-vocas/{setVocaId}")
	public ResponseEntity<Object> handleUpdateSetVocasById(@PathVariable UUID setVocaId,
			@Valid @RequestBody SetVocaRequest setVocaRequest) throws NotFoundException {
		// Custom Validate:
		setVocaRequest.setId(setVocaId);
		setVocaDataValidate.validateSetVocaIdNotExist(setVocaRequest.getId());
		setVocaDataValidate.validateAuthorNotExistById(setVocaRequest.getAuthorId());
		setVocaDataValidate.validateSetVocasOverRange(setVocaRequest.getAuthorId(),
				getSetVocaSpecification(setVocaRequest.getAuthorId()));
		setVocaDataValidate.validate(setVocaRequest);
		// Update SetVoca:
		SetVoca oldSetVoca = setVocaService.findById(setVocaId);
		SetVoca setVoca = setVocaRequest.getSetVoca();
		oldSetVoca.setAuthor(setVoca.getAuthor());
		oldSetVoca.setSetName(setVoca.getSetName());
		// Save SetVoca:
		SetVoca savedSetVoca = setVocaService.save(oldSetVoca);

		SetVocaResponse setVocaResponse = new SetVocaResponse(savedSetVoca);
		setVocaResponse.setTotalVocas(vocaService.countVocaBySetVocaId(setVocaId).intValue());

		return ResponseEntity.ok(setVocaResponse);
	}

	// Get SetVocas by AuthorId:
	@GetMapping("/users/{authorId}/set-vocas")
	public ResponseEntity<Object> handleGetSetVocasCreatedByAuthor(@PathVariable UUID authorId)
			throws NotFoundException {
		setVocaDataValidate.validateAuthorNotExistById(authorId);
		Specification_SetVoca specification = getSetVocaSpecification(authorId);
		Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "createdDate");
		List<SetVoca> setVocas = setVocaService.findAll(specification, pageable);
		List<SetVocaResponse> setVocaResponses = setVocas.stream().map(setVoca -> {
			SetVocaResponse setVocaResponse = new SetVocaResponse(setVoca);
			setVocaResponse.setTotalVocas(vocaService.countVocaBySetVocaId(setVoca.getId()).intValue());
			return setVocaResponse;
		}).collect(Collectors.toList());
		return ResponseEntity.ok(setVocaResponses);
	}

	private Specification_SetVoca getSetVocaSpecification(UUID authorId) {
		Specification_SetVoca specification = new Specification_SetVoca();
		User author = userService.findById(authorId);
		specification.add(new _SearchCriteria("author", _SearchOperator.EQUAL, author));
		return specification;
	}
}
