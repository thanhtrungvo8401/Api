package fusikun.com.api.dao;

import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.TestGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestGroupRepository extends
        JpaRepository<TestGroup, UUID>,
        JpaSpecificationExecutor<TestGroup>,
        PagingAndSortingRepository<TestGroup, UUID> {
    TestGroup findByOwner(User owner);
}
