package fusikun.com.api.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import fusikun.com.api.model.Role;

public class RoleResponse {
	private UUID id;
	private String roleName;
	private String description;
	private Boolean isActive;
	private Date createdDate;
	private Date updatedDate;
	// SETTER ONLY:
	private List<AuthResponse> auths;

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

	public List<AuthResponse> getAuths() {
		return auths;
	}

	public void setAuths(List<AuthResponse> auths) {
		this.auths = auths;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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
}
