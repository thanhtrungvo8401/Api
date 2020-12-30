package fusikun.com.api.service;

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

	public User updateUser(User user) {
		return userRepository.saveAndFlush(user);
	}
}
