package fusikun.com.api.dao;

import fusikun.com.api.model.study.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CenterRepository extends JpaRepository<Center, UUID> {
    Optional<Center> findByCenterName(String centerName);
}
