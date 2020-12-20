package fusikun.com.api.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails{
	
	private static final long serialVersionUID = 1317367368138070184L;
	private String username;
	private String password;
	private boolean isActive;
	private String accessToken;
	private List<GrantedAuthority> authorities;

	public JwtUserDetails() {
	}
	
	public JwtUserDetails(User user) {
		if (user != null) {
			this.username = user.getUsername();
			this.password = user.getPassword();
			this.isActive = user.getIsActive();
			this.accessToken = user.getAccessToken();
			this.authorities = Arrays.asList(new SimpleGrantedAuthority("USER_ROLE"));
		}
		
	}

	public String getAccessToken() {
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
		return this.isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive;
	}

}
