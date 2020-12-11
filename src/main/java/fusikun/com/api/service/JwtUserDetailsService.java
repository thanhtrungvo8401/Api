package fusikun.com.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fusikun.com.api.model.JwtUserDetails;
import fusikun.com.api.model.User;
/**
 * 
 * @author thtrungvo
 *	this method implements UserDetailsService of Spring-Security
 * 	show the way we can get the User (DB, ...)
 * 	by method: loadUserByUsername
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserService userService;
	
	@Override
	public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		return new JwtUserDetails(user);
	}

}
