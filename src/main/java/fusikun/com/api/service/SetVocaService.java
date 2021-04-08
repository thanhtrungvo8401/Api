package fusikun.com.api.service;

import java.util.*;
import java.util.stream.Collectors;

import fusikun.com.api.dtoREQ.SetVocaRequest;
import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoRES.SetVocaResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.specificationSearch.SearchHelpers_SetVocas;
import fusikun.com.api.specificationSearch._SearchCriteria;
import fusikun.com.api.utils.SortHelper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Autowired
    UserService userService;

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
        return new ArrayList<>();
    }

    public List<SetVoca> findAll(Specification_SetVoca specification) {
        return setVocaRepository.findAll(specification);
    }

    public SetVoca findById(UUID id) {
        Optional<SetVoca> setVocaOpt = setVocaRepository.findById(id);
        return setVocaOpt.orElse(null);
    }

    public Long count(Specification_SetVoca specification) {
        return setVocaRepository.count(specification);
    }

    public void delete(SetVoca setVoca) {
        setVocaRepository.delete(setVoca);
    }

    public SetVocaResponse _createSetVocas(SetVocaRequest req) {
        SetVoca setVoca = req.getSetVoca();
        return new SetVocaResponse(save(setVoca));
    }

    public SetVocaResponse _updateSetVocasById(SetVocaRequest req, UUID id) {
        SetVoca oldSetVoca = findById(id);
        oldSetVoca.setSetName(req.getSetName());
        oldSetVoca.setMaxVoca(req.getMaxVoca());
        return new SetVocaResponse(save(oldSetVoca));
    }

    public List<SetVocaResponse> _getSetVocasByAuthorId(UUID id) {
        Specification_SetVoca specification = new Specification_SetVoca();
        specification.add(new _SearchCriteria(
                "author",
                SearchOperator.EQUAL,
                userService.findById(id)
        ));
        List<SetVoca> setVocas = findAll(specification);
        return setVocas.stream()
                .map(SetVocaResponse::new)
                .collect(Collectors.toList());
    }

    public SetVocaResponse _getSetVocasById(UUID id) {
        return new SetVocaResponse(findById(id));
    }

    public SetVocasManagement _getSetVocasManagementByCenterIdAndRoleName(
            UUID centerId,
            String rolename,
            String filters,
            String limit,
            String page,
            String sortBy,
            String order
    ) {
        List<UUID> userIds = userService._getUserIdsBaseOnCenterIdAndRoleName(centerId, rolename);
        Specification_SetVoca specification = new Specification_SetVoca();
        if (!userIds.isEmpty()) {
            specification.add(new _SearchCriteria(
                    "author",
                    SearchOperator.IN,
                    userIds,
                    "id",
                    ApiDataType.UUID_TYPE
            ));
        }
        Specification_SetVoca specification2 = new SearchHelpers_SetVocas(
                specification,
                filters
        ).getSpecification(Arrays.asList(
                "id," + ApiDataType.UUID_TYPE,
                "maxVoca," + ApiDataType.INTEGER_TYPE,
                "createdDate," + ApiDataType.DATE_TYPE,
                "updatedDate," + ApiDataType.DATE_TYPE,
                "setName," + ApiDataType.STRING_TYPE,
                "author.id," + ApiDataType.UUID_TYPE,
                "totalVocas," + ApiDataType.INTEGER_TYPE
        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        Long total = count(specification2);
        List<SetVocaResponse> list = findAll(specification2, pageable)
                .stream()
                .map(SetVocaResponse::new)
                .collect(Collectors.toList());
        return new SetVocasManagement(list, total);
    }

    public SetVocaResponse _deleteById(UUID id) {
        SetVoca setVoca = findById(id);
        delete(setVoca);
        return new SetVocaResponse(setVoca);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class SetVocasManagement extends ObjectsManagementList<SetVocaResponse> {
        public SetVocasManagement(List<SetVocaResponse> list, Long total) {
            super(list, total);
        }
    }
}
