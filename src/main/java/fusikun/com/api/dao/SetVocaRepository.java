package fusikun.com.api.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.study.SetVoca;

@Repository
public interface SetVocaRepository extends JpaRepository<SetVoca, UUID>, JpaSpecificationExecutor<SetVoca> {

    @Query(value = "SELECT * FROM set_voca as S " +
            "WHERE SUBSTR(setName, 1, 11) " +
            "in ?1" +
            "", nativeQuery = true)
    List<SetVoca> findGeneralSetVocaBaseOnTestGroup(List<String> namesPrefix);
}
