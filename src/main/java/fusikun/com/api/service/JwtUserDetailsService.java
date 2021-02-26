package fusikun.com.api.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.model.app.User;

/**
 * 
 * @author thtrungvo this method implements UserDetailsService of
 *         Spring-Security show the way we can get the User (DB, ...) by method:
 *         loadUserByUsername
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserService userService;

	@Override
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByEmail(username);
		return new JwtUserDetails(user);
	}

	public JwtUserDetails loadUserByUserId(UUID uuid) {
		User user = userService.findById(uuid);
		return new JwtUserDetails(user);
	}

}
