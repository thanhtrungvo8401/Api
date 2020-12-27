package fusikun.com.api.dto;

import fusikun.com.api.model.Auth;

public class AuthResponse {
	private Long id;
	private Boolean isActive;
	private MenuResponse menus;

	public AuthResponse() {
	}

	public AuthResponse(Auth auth, MenuResponse menus) {
		this.id = auth.getId();
		this.isActive = auth.getIsActive();
		this.menus = menus;
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

	public MenuResponse getMenus() {
		return menus;
	}

	public void setMenus(MenuResponse menus) {
		this.menus = menus;
	}
}
