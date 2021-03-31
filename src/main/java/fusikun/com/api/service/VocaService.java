package fusikun.com.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.VocaRepository;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.model.study.Voca;
import fusikun.com.api.specificationSearch.Specification_Voca;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.enums.SearchOperator;

@Service
public class VocaService {
	@Autowired
	VocaRepository vocaRepository;

	@Autowired SetVocaService setVocaService;

	public Voca findById(UUID id) {
		Optional<Voca> optVoca = vocaRepository.findById(id);
		return optVoca.orElse(null);
	}

	public Voca save(Voca entity) {
		if (entity.getId() == null) {
			entity.setCreatedDate(new Date());
		}
		entity.setUpdatedDate(new Date());
		if (entity.getIsActive() == null) {
			entity.setIsActive(true);
		}
		return vocaRepository.save(entity);
	}

	public List<Voca> findAll(Specification_Voca specification, Pageable pageable) {
		Page<Voca> page = vocaRepository.findAll(specification, pageable);
		if (page.hasContent())
			return page.getContent();
		return new ArrayList<>();

	}

	public Long count(Specification_Voca specification) {
		return vocaRepository.count(specification);
	}

	public Long countVocaBySetVocaId(UUID setVocaId) {
		Specification_Voca specification = getVocaSpecification(setVocaId);
		return count(specification);
	}

	public void deleteById(UUID id) {
		vocaRepository.deleteById(id);
	}

	public Voca create(Voca entity) {
		SetVoca setVoca = setVocaService.findById(entity.getSetVoca().getId());
		setVoca.increaseVoca();
		setVocaService.save(setVoca);
		return save(entity);
	}

	public void delete(Voca voca) {
		vocaRepository.delete(voca);
	}

	private Specification_Voca getVocaSpecification(UUID setVocaId) {
		Specification_Voca specification = new Specification_Voca();
		SetVoca setVoca = new SetVoca();
		setVoca.setId(setVocaId);
		specification.add(new _SearchCriteria("setVoca", SearchOperator.EQUAL, setVoca));
		return specification;
	}
}
