package fusikun.com.api.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.study.Voca;

@Repository
public interface VocaRepository extends JpaRepository<Voca, UUID>, JpaSpecificationExecutor<Voca> {
    @Query(value = "SELECT * FROM voca as V "+
            "INNER JOIN set_voca as S "+
            "ON V.setId = S.id and S.setName LIKE %?1% " +
            "ORDER BY RAND() LIMIT ?2 ",  nativeQuery = true)
    List<Voca> findRandVoca(String level, Integer limit);

    @Query(value = "SELECT * FROM voca as V "
            + "WHERE setId in ?1 "
            + "ORDER BY RAND() "
            + "LIMIT ?2 "
            , nativeQuery = true)
    List<Voca> findRandVocaBaseOnTestGroup(List<UUID> setVocasId, Integer limit);
}