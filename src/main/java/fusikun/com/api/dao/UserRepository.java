package fusikun.com.api.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.User;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface UserRepository
		extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, UUID> {
	Optional<User> findByEmail(String email);

	@Query(value = "select * from user as u where u.roleId = ?1", nativeQuery = true)
	List<User> findByRoleId(UUID roleId);

	Long countByEmail(String email);
}
