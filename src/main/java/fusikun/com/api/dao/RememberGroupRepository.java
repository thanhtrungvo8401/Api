package fusikun.com.api.dao;

import fusikun.com.api.model.study.RememberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface RememberGroupRepository extends
        JpaRepository<RememberGroup, UUID>,
        JpaSpecificationExecutor<RememberGroup>,
        PagingAndSortingRepository<RememberGroup, UUID> {
}
