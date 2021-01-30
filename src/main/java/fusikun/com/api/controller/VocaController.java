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

	@PostMapping("/vocas")
	public ResponseEntity<Object> handleCreateVoca(@Valid @RequestBody VocaRequest vocaRequest)
			throws NotFoundException {
		vocaDataValidate.validate(vocaRequest);
		vocaDataValidate.validateExistSetVocaById(vocaRequest.getSetId());
		vocaDataValidate.validateOverMaxVoca(vocaRequest.getSetId(),
				getVocaSpecificationFromSetVocasId(vocaRequest.getSetId()));
		
		Voca voca = vocaRequest.getVocaObject();
		vocaService.save(voca);

		return ResponseEntity.status(HttpStatus.CREATED).body(new VocaResponse(voca));
	}

	@GetMapping("/set-vocas/{id}/vocas")
	public ResponseEntity<Object> handleGetVocasInSetVoca(@PathVariable UUID id) throws NotFoundException {
		vocaDataValidate.validateExistSetVocaById(id);
		Specification_Voca specification = getVocaSpecificationFromSetVocasId(id);
		Pageable pageable = PageRequest.of(0, 100, Direction.DESC, "createdDate");
		List<Voca> vocas = vocaService.findAll(specification, pageable);
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
