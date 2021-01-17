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

	@PostMapping("/set-vocas")
	public ResponseEntity<Object> handleCreateSetVocas(@Valid @RequestBody SetVocaRequest setVocaRequest)
			throws NotFoundException {
		setVocaDataValidate.validate(setVocaRequest);
		setVocaDataValidate.validateAuthorNotExistById(setVocaRequest.getAuthorId());
		SetVoca setVoca = setVocaRequest.getSetVoca();
		setVocaService.save(setVoca);
		SetVocaResponse setVocaResponse = new SetVocaResponse(setVoca);
		return ResponseEntity.status(HttpStatus.CREATED).body(setVocaResponse);
	}

	@GetMapping("/users/{authorId}/set-vocas")
	public ResponseEntity<Object> handleGetSetVocas(@PathVariable UUID authorId) throws NotFoundException {
		Specification_SetVoca specification = new Specification_SetVoca();
		setVocaDataValidate.validateAuthorNotExistById(authorId);
		User author = userService.findById(authorId);
		specification.add(new _SearchCriteria("author", _SearchOperator.EQUAL, author));
		Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "createdDate");
		List<SetVoca> setVocas = setVocaService.findAll(specification, pageable);
		List<SetVocaResponse> setVocaResponses = setVocas.stream().map(setVoca -> new SetVocaResponse(setVoca))
				.collect(Collectors.toList());
		return ResponseEntity.ok(setVocaResponses);
	}

}
