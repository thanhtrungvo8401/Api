package fusikun.com.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fusikun.com.api.dao.UserRepository;
import fusikun.com.api.model.User;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public User findByUsername(String username) {
		Optional<User> optUser = userRepository.findByUsername(username);
		if(optUser.isPresent())
			return optUser.get();
		else return null;
	}
}
