package fusikun.com.api.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fusikun.com.api.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID>, JpaSpecificationExecutor<Menu> {
	List<Menu> findByName(String name);
	
	Integer countByName(String name);

	Integer countByUrl(String url);

	@Query(value = "select * from menu as M where M.parentId IS NULL", nativeQuery = true)
	List<Menu> findAllParentMenu();
}
