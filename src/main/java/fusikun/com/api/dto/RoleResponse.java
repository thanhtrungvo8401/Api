package fusikun.com.api.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fusikun.com.api.model.Auth;
import fusikun.com.api.model.Menu;
import fusikun.com.api.model.Role;

public class RoleResponse {
	private Long id;
	private String roleName;
	private String description;
	private Boolean isActive;
	private Date createdDate;
	private Date updatedDate;
	// SETTER ONLY:
	private List<Menu> menus = new ArrayList<>();

	public RoleResponse() {
	}

	public RoleResponse(Role role) {
		this.id = role.getId();
		this.roleName = role.getRoleName();
		this.description = role.getDescription();
		this.isActive = role.getIsActive();
		this.createdDate = role.getCreatedDate();
		this.updatedDate = role.getUpdatedDate();
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	private List<Auth> auths;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public List<Auth> getAuths() {
		return auths;
	}

	public void setAuths(List<Auth> auths) {
		this.auths = auths;
	}
}
