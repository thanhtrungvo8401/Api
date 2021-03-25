package fusikun.com.api.service;

import fusikun.com.api.dao.TestGroupRepository;
import fusikun.com.api.model.study.TestGroup;
import fusikun.com.api.specificationSearch.Specification_TestGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class TestGroupService {
    @Autowired
    TestGroupRepository repository;

    public TestGroup save(TestGroup entity) {
        if (entity.getId() == null) {
            entity.setCreatedDate(new Date());
        }
        entity.setIsActive(true);
        entity.setUpdatedDate(new Date());
        return repository.save(entity);
    }

    public TestGroup findById(UUID id) {
        Optional<TestGroup> testGroupOpt =
                repository.findById(id);
        return testGroupOpt.orElse(null);
    }

    public Long count(Specification_TestGroup specification) {
        return repository.count(specification);
    }

    public List<TestGroup> findAll(
            Specification_TestGroup specification,
            Pageable pageable) {
        Page<TestGroup> page =
                repository.findAll(specification, pageable);
        if (page.hasContent()) {
            return page.getContent();
        }
        return new ArrayList<>();
    }

    public List<TestGroup> findAll(Specification_TestGroup specification) {
        return repository.findAll(specification);
    }

    public void delete(TestGroup entity) {
        repository.delete(entity);
    }
}
