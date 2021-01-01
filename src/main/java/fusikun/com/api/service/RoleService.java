package fusikun.com.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.RoleRepository;
import fusikun.com.api.model.Role;

@Service
@Transactional(rollbackOn = Exception.class)
public class RoleService {
	@Autowired
	RoleRepository roleRepository;

	public void deleteById(Long id) {
		roleRepository.deleteById(id);
	}

	public Role findRoleById(Long id) {
		Optional<Role> optRole = roleRepository.findById(id);
		if (optRole.isPresent())
			return optRole.get();
		else
			return null;
	}

	public Role save(Role entity) {
		if (entity.getId() == null) {
			entity.setCreatedDate(new Date());
		}
		entity.setUpdatedDate(new Date());
		if (entity.getIsActive() == null) {
			entity.setIsActive(true);
		}
		return roleRepository.save(entity);
	}

	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	public Integer coutByRoleName(String roleName) {
		return roleRepository.countByRoleName(roleName);
	}
	
	public Long count() {
		return roleRepository.count();
	}
	
	public Boolean existByRoleName(String roleName) {
		return roleRepository.countByRoleName(roleName) > 0;
	}
}
