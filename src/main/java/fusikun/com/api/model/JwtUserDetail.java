package fusikun.com.api.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetail implements UserDetails{
	
	private static final long serialVersionUID = 1317367368138070184L;
	private String username;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;

	public JwtUserDetail() {
	}
	
	public JwtUserDetail(User user) {
		if (user != null) {
			this.username = user.getUsername();
			this.password = user.getPassword();
			this.active = user.isActive();
			this.authorities = Arrays.asList(new SimpleGrantedAuthority("USER_ROLE"));
		}
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}

}
