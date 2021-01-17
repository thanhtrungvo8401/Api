package fusikun.com.api.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.study.SetVoca;

@Repository
public interface SetVocaRepository extends JpaRepository<SetVoca, UUID>, JpaSpecificationExecutor<SetVoca> {

}
