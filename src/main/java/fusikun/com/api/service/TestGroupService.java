package fusikun.com.api.service;

import fusikun.com.api.dao.TestGroupRepository;
import fusikun.com.api.dtoREQ.TestGroupRequest;
import fusikun.com.api.dtoRES.ObjectsManagementList;
import fusikun.com.api.dtoRES.TestGroupResponse;
import fusikun.com.api.enums.ApiDataType;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.TestGroup;
import fusikun.com.api.specificationSearch.SearchHelpers_TestGroup;
import fusikun.com.api.specificationSearch.Specification_TestGroup;
import fusikun.com.api.utils.Constant;
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
public class TestGroupService {
    @Autowired
    TestGroupRepository repository;

    @Autowired
    UserService userService;

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

    public TestGroupResponse _createTestGroup(TestGroupRequest req) {
        TestGroup testGroup = req.getTestGroup();
        return new TestGroupResponse(save(testGroup));
    }

    public TestGroupResponse _getTestGroupsByOwnerId(UUID id) {
        TestGroup testGroup = repository.findByOwner(new User(id));
        if (testGroup != null) {
            return new TestGroupResponse(testGroup);
        } else {
            TestGroupRequest testGroupRequest =
                    new TestGroupRequest(id, Constant.DEFAULT_TEST_GROUP_NUMBER,"0_", "0_", "0_", "0_", "0_", "0_");
            return _createTestGroup(testGroupRequest);
        }
    }

    public TestGroupManagement _getTestGroupManagement(
            String filters,
            String limit,
            String page,
            String sortBy,
            String order
    ) {
        Specification_TestGroup specification =
                new SearchHelpers_TestGroup(new Specification_TestGroup(), filters)
                        .getSpecification(Arrays.asList(
                                "id," + ApiDataType.UUID_TYPE,
                                "vocaCodes," + ApiDataType.STRING_TYPE,
                                "createdDate," + ApiDataType.DATE_TYPE,
                                "updatedDate," + ApiDataType.DATE_TYPE,
                                "name," + ApiDataType.STRING_TYPE,
                                "owner.id," + ApiDataType.UUID_TYPE
                        ));
        Pageable pageable = SortHelper.getSort(limit, page, sortBy, order);
        Long total = count(specification);
        List<TestGroupResponse> testGroupResponses =
                findAll(specification, pageable)
                        .stream().map(TestGroupResponse::new)
                        .collect(Collectors.toList());
        return new TestGroupManagement(testGroupResponses, total);
    }

    public TestGroupResponse _updateTestGroupById(TestGroupRequest req, UUID id) {
        TestGroup oldTestGroup = findById(id);
        oldTestGroup.setMyVoca(req.getMyVoca());
        oldTestGroup.setN5(req.getN5());
        oldTestGroup.setN4(req.getN4());
        oldTestGroup.setN3(req.getN3());
        oldTestGroup.setN2(req.getN2());
        oldTestGroup.setN1(req.getN1());
        oldTestGroup.setNumber(req.getNumber());
        return new TestGroupResponse(save(oldTestGroup));
    }

    public TestGroupResponse _deleteById(UUID id) {
        TestGroup testGroup = findById(id);
        delete(testGroup);
        return new TestGroupResponse(testGroup);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class TestGroupManagement extends ObjectsManagementList<TestGroupResponse> {
        public TestGroupManagement(List<TestGroupResponse> list, Long total) {
            super(list, total);
        }
    }
}
