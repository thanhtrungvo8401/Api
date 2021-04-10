package fusikun.com.api.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fusikun.com.api.dtoRES.UserInfoObject;
import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.model.app.User;
import fusikun.com.api.utils.Constant;

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
		return new JwtUserDetails(user, true);
	}

	public JwtUserDetails loadUserByUserInfo(UserInfoObject userInfo) {
		String[] basicRoleArr = { Constant.ADMIN_ROLE, Constant.STUDENT_ROLE };
		boolean isWithoutMenus = Arrays.asList(basicRoleArr).contains(userInfo.getRoleName());
		User user = userService.findById(userInfo.getId());
		return isWithoutMenus ? new JwtUserDetails(user, true) : new JwtUserDetails(user);
	}
}
