package fusikun.com.api.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.study.Voca;

@Repository
public interface VocaRepository extends JpaRepository<Voca, UUID>, JpaSpecificationExecutor<Voca> {

}
