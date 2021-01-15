package fusikun.com.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.dao.UserRepository;
import fusikun.com.api.model.app.User;
import fusikun.com.api.specificationSearch.Specification_User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleService roleService;

	public User save(User user) {
		if (user.getId() == null) {
			user.setCreatedDate(new Date());
		}
		if (user.getIsActive() == null) {
			user.setIsActive(true);
		}
		user.setUpdatedDate(new Date());

		return userRepository.save(user);
	}

	public Long count(Specification_User specification) {
		return userRepository.count(specification);
	}

	public List<User> findAll(Specification_User specification, Pageable pageable) {
		Page<User> page = userRepository.findAll(specification, pageable);
		if (page.hasContent()) {
			return page.getContent();
		}
		return new ArrayList<User>();
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(UUID id) {
		Optional<User> optUser = userRepository.findById(id);
		if (optUser.isPresent())
			return optUser.get();
		else
			return null;
	}

	public User findByEmail(String email) {
		Optional<User> optUser = userRepository.findByEmail(email);
		if (optUser.isPresent())
			return optUser.get();
		else
			return null;
	}

	public List<User> findByRoleId(UUID roleId) {
		return userRepository.findByRoleId(roleId);
	}

	public Long countByEmail(String email) {
		return userRepository.countByEmail(email);
	}

	public void deleteById(UUID id) {
		userRepository.deleteById(id);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}
}
