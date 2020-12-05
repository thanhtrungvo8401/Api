package fusikun.com.api.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * 
 * @author thtrungvo
 *	this method implements UserDetailsService of Spring-Security
 * 	show the way we can get the User (DB, ...)
 * 	by method: loadUserByUsername
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User(username, "1234", new ArrayList<>());
	}

}
