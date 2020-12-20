package fusikun.com.api.dao;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.User;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
