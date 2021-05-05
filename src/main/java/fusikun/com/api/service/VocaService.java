package fusikun.com.api.service;

import java.util.*;
import java.util.stream.Collectors;

import fusikun.com.api.dtoREQ.VocaRequest;
import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoRES.VocaResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.specificationSearch.SearchHelpers_Vocas;
import fusikun.com.api.utils.SortHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class VocaService {
    @Autowired
    VocaRepository vocaRepository;

    @Autowired
    SetVocaService setVocaService;

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

    public List<Voca> findAll(Specification_Voca specification) {
        return vocaRepository.findAll(specification);
    }

    public Long count(Specification_Voca specification) {
        return vocaRepository.count(specification);
    }

    public void delete(Voca voca) {
        vocaRepository.delete(voca);
    }

    public VocaResponse _createVoca(VocaRequest req) {
        Voca voca = req.getVocaObject();
        SetVoca setVoca =
                setVocaService.findById(voca.getSetVoca().getId());
        setVoca.increaseVoca();
        setVocaService.save(setVoca);
        // we are not checking unique for CODE
        save(voca);
        return new VocaResponse(findById(voca.getId()));
    }

    public VocaResponse _updateVocaById(VocaRequest req, UUID id) {
        Voca oldVoca = findById(id);
        oldVoca.setVoca(req.getVoca());
        oldVoca.setMeaning(req.getMeaning());
        oldVoca.setSentence(req.getSentence());
        oldVoca.setNote(req.getNote());
        return new VocaResponse(save(oldVoca));
    }

    public VocaResponse _deleteById(UUID id) {
        Voca voca = findById(id);
        SetVoca setVoca = voca.getSetVoca();
        setVoca.decreaseVoca();
        setVocaService.save(setVoca); // dao
        delete(voca); // dao
        return new VocaResponse(voca);
    }

    public VocasManagement _getVocasManagement(
            String filters,
            String limit,
            String page,
            String sortBy,
            String order) {
        Specification_Voca specification =
                new SearchHelpers_Vocas(new Specification_Voca(), filters)
                        .getSpecification(Arrays.asList(
                                "id," + ApiDataType.UUID_TYPE,
                                "code,"+ApiDataType.INTEGER_TYPE,
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
        Long total = count(specification);
        List<VocaResponse> vocaResponses =
                findAll(specification, pageable)
                        .stream().map(VocaResponse::new)
                        .collect(Collectors.toList());
        return new VocasManagement(vocaResponses, total);
    }

    public List<VocaResponse> _getVocasBySetId(UUID id) {
        Specification_Voca specification = new Specification_Voca();
        specification.add(new _SearchCriteria(
                "setVoca",
                SearchOperator.EQUAL,
                setVocaService.findById(id)
        ));
        return findAll(specification)
                .stream().map(VocaResponse::new)
                .collect(Collectors.toList());
    }

    public List<VocaResponse> _getRandomVocaByLevel(String level, Integer limit) {
        return vocaRepository
                .findRandVoca(level, limit)
                .stream().map(VocaResponse::new)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class VocasManagement extends ObjectsManagementList<VocaResponse> {
        public VocasManagement(List<VocaResponse> list, Long total) {
            super(list, total);
        }
    }
}
