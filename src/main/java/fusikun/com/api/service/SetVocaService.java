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
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.dao.SetVocaRepository;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.specificationSearch.Specification_SetVoca;

@Service
@Transactional(rollbackFor = Exception.class)
public class SetVocaService {

	@Autowired
	SetVocaRepository setVocaRepository;

	public SetVoca save(SetVoca entity) {
		if (entity.getId() == null) {
			entity.setCreatedDate(new Date());
		}
		entity.setUpdatedDate(new Date());
		if (entity.getIsActive() == null) {
			entity.setIsActive(true);
		}

		return setVocaRepository.save(entity);
	}

	public List<SetVoca> findAll(Specification_SetVoca specification, Pageable pageable) {
		Page<SetVoca> page = setVocaRepository.findAll(specification, pageable);
		if (page.hasContent()) {
			return page.getContent();
		}
		return new ArrayList<SetVoca>();
	}

	public SetVoca findById(UUID id) {
		Optional<SetVoca> setVocaOpt = setVocaRepository.findById(id);
		if (setVocaOpt.isPresent())
			return setVocaOpt.get();
		else
			return null;
	}

	public Long count(Specification_SetVoca specification) {
		return setVocaRepository.count(specification);
	}
}
