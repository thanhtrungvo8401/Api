package fusikun.com.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.dao.UserRepository;
import fusikun.com.api.model.User;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {
	@Autowired
	UserRepository userRepository;

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

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		Optional<User> optUser = userRepository.findById(id);
		if (optUser.isPresent())
			return optUser.get();
		else
			return null;
	}

	public User findByUsername(String username) {
		Optional<User> optUser = userRepository.findByUsername(username);
		if (optUser.isPresent())
			return optUser.get();
		else
			return null;
	}

	public List<User> findByRoleId(Long roleId) {
		return userRepository.findByRoleId(roleId);
	}

	public Long countByUsername(String username) {
		return userRepository.countByUsername(username);
	}

	public Long countByEmail(String email) {
		return userRepository.countByEmail(email);
	}
}
