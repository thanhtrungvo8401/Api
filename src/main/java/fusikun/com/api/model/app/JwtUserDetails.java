package fusikun.com.api.model.app;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1317367368138070184L;
	private UUID id;
	private String email;
	private String password;
	private boolean isActive;
	private String accessToken;
	private Role role;
	private List<Menu> menus;

	public JwtUserDetails() {
	}

	public JwtUserDetails(User user) {
		if (user != null) {
			this.id = user.getId();
			this.email = user.getEmail();
			this.password = user.getPassword();
			this.isActive = user.getIsActive();
			this.accessToken = user.getAccessToken();
			this.role = user.getRole();
			List<Auth> auths = role.getAuths();
			this.menus = auths.stream().filter(auth -> auth.getIsActive()).map(auth -> auth.getMenu())
					.filter(menu -> menu.getMethod() != null).collect(Collectors.toList());
		}
	}

	public JwtUserDetails(User user, Boolean isWithoutMenus) {
		if (user != null) {
			this.id = user.getId();
			this.email = user.getEmail();
			this.password = user.getPassword();
			this.isActive = user.getIsActive();
			this.accessToken = user.getAccessToken();
			this.role = user.getRole();
			this.role.getRoleName(); // to load Role role value;
		}
	}

	public UUID getId() {
		return id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Role getRole() {
		return role;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return email;
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

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
}
