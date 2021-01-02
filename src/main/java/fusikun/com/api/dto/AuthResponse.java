package fusikun.com.api.dto;

import fusikun.com.api.model.Auth;

public class AuthResponse {
	private Long id;
	private Boolean isActive;
	private MenuResponse menu;

	public AuthResponse() {
	}

	public AuthResponse(Auth auth, MenuResponse menu) {
		this.id = auth.getId();
		this.isActive = auth.getIsActive();
		this.menu = menu;
	}
	
	public AuthResponse(Auth auth) {
		this.id = auth.getId();
		this.isActive = auth.getIsActive();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public MenuResponse getMenu() {
		return menu;
	}

	public void setMenu(MenuResponse menu) {
		this.menu = menu;
	}
}
