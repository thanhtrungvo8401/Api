package fusikun.com.api.service;


import fusikun.com.api.dao.RememberGroupRepository;
import fusikun.com.api.dtoREQ.RememberGroupRequest;
import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoRES.RememberGroupResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.enums.SearchOperator;
import fusikun.com.api.model.study.RememberGroup;
import fusikun.com.api.specificationSearch.SearchHelpers_RememberGroup;
import fusikun.com.api.specificationSearch.Specification_RememberGroup;
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

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
public class RememberGroupService {
    @Autowired
    RememberGroupRepository rememberGroupRepository;

    @Autowired
    UserService userService;

    public RememberGroup save(RememberGroup entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
        }
        entity.setIsActive(true);
        entity.setUpdatedDate(new Date());
        return rememberGroupRepository.save(entity);
    }

    public RememberGroup findById(UUID id) {
        Optional<RememberGroup> rememberGroupOpt =
                rememberGroupRepository.findById(id);
        return rememberGroupOpt.orElse(null);
    }

    public Long count(Specification_RememberGroup specification) {
        return rememberGroupRepository.count(specification);
    }

    public void delete(RememberGroup entity) {
        rememberGroupRepository.delete(entity);
    }

    public List<RememberGroup> findAll(
            Specification_RememberGroup specification,
            Pageable pageable) {
        Page<RememberGroup> page =
                rememberGroupRepository.findAll(specification, pageable);
        if (page.hasContent()) {
            return page.getContent();
        }
        return new ArrayList<>();
    }

    public List<RememberGroup> findAll(Specification_RememberGroup specification) {
        return rememberGroupRepository.findAll(specification);
    }

    // call from controller:
    public RememberGroupResponse _createRememberGroup(RememberGroupRequest request) {
        RememberGroup rememberGroup = request.getRemembersGroup();
        return new RememberGroupResponse(save(rememberGroup));
    }

    public List<RememberGroupResponse> _findAllByOwnerId(UUID id) {
        Specification_RememberGroup specification = new Specification_RememberGroup();
        specification.add(new _SearchCriteria("owner", SearchOperator.EQUAL,
                userService.findById(id)));

        List<RememberGroup> rememberGroups = findAll(specification);
        return rememberGroups.stream().map(RememberGroupResponse::new)
                .collect(Collectors.toList());
    }

    public RemGroupManagement _findManagementByFilters(String filters, String limit, String page,
                                                       String sortBy, String order) {
        Specification_RememberGroup specification =
                new SearchHelpers_RememberGroup(new Specification_RememberGroup(), filters)
                        .getSpecification(Arrays.asList(
                                "id," + ApiDataType.UUID_TYPE,
                                "vocaCodes," + ApiDataType.STRING_TYPE,
                                "createdDate," + ApiDataType.DATE_TYPE,
                                "updatedDate," + ApiDataType.DATE_TYPE,
                                "name," + ApiDataType.DATE_TYPE,
                                "owner.id," + ApiDataType.UUID_TYPE
                        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        List<RememberGroup> rememberGroups = findAll(specification, pageable);
        List<RememberGroupResponse> list =
                rememberGroups.stream().map(RememberGroupResponse::new)
                        .collect(Collectors.toList());
        Long total = count(specification);
        return new RemGroupManagement(list, total);
    }

    public RememberGroupResponse _updateById(RememberGroupRequest request, UUID id) {
        RememberGroup oldRemGroup = findById(id);
        oldRemGroup.setName(request.getName());
        oldRemGroup.setVocaCodes(request.getVocaCodes());
        oldRemGroup.setActiveCodes(request.getActiveCodes());
        return new RememberGroupResponse(save(oldRemGroup));
    }

    public RememberGroupResponse _deleteById(UUID id) {
        RememberGroup rememberGroup = findById(id);
        delete(rememberGroup);
        return new RememberGroupResponse(rememberGroup);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class RemGroupManagement extends ObjectsManagementList<RememberGroupResponse> {
        public RemGroupManagement(List<RememberGroupResponse> list, Long total) {
            super(list, total);
        }
    }
}