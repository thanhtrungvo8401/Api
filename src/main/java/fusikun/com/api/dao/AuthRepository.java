package fusikun.com.api.dao;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import fusikun.com.api.model.app.Auth;
import fusikun.com.api.model.app.Menu;
import fusikun.com.api.model.app.Role;

public interface AuthRepository extends JpaRepository<Auth, UUID>, JpaSpecificationExecutor<Auth> {
	Integer countByRoleAndMenu(Role role, Menu menu);
}
