package fusikun.com.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fusikun.com.api.model.Auth;
import fusikun.com.api.model.Menu;
import fusikun.com.api.model.Role;

public interface AuthRepository extends JpaRepository<Auth, Long>, JpaSpecificationExecutor<Auth> {
	Integer countByRoleAndMenu(Role role, Menu menu);
}
