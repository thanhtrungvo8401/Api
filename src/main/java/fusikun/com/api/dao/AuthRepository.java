package fusikun.com.api.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fusikun.com.api.model.Auth;
import fusikun.com.api.model.Menu;
import fusikun.com.api.model.Role;

public interface AuthRepository extends JpaRepository<Auth, UUID>, JpaSpecificationExecutor<Auth> {
	Integer countByRoleAndMenu(Role role, Menu menu);
}
