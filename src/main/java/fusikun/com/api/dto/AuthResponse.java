package fusikun.com.api.dto;

import java.util.UUID;

import fusikun.com.api.model.app.Auth;

public class AuthResponse {
	private UUID id;
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
