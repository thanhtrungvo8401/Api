package fusikun.com.api.service;


import fusikun.com.api.dao.RememberGroupRepository;
import fusikun.com.api.model.study.RememberGroup;
import fusikun.com.api.specificationSearch.Specification_RememberGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(rollbackFor = Exception.class)
public class RememberGroupService {
    @Autowired
    RememberGroupRepository rememberGroupRepository;

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

}