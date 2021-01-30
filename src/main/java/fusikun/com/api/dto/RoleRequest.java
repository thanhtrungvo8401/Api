package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.app.Role;
import fusikun.com.api.utils.ConstantErrorCodes;

public class RoleRequest {
	private UUID id;

	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@NotBlank(message = ConstantErrorCodes.NOT_BLANK)
	private String roleName;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@NotBlank(message = ConstantErrorCodes.NOT_BLANK)
	private String description;

	private Boolean isActive;

	public Role getRole() {
		Role role = new Role();
		role.setId(this.id);
		role.setRoleName(this.roleName);
		role.setDescription(this.description);
		role.setIsActive(this.isActive);
		return role;
	}

	public RoleRequest() {
	}

	public RoleRequest(UUID id, String roleName, String description) {
		this.id = id;
		this.roleName = roleName;
		this.description = description;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	@Override
	public String toString() {
		return "RoleRequest [id=" + id + ", roleName=" + roleName + ", description=" + description + ", isActive="
				+ isActive + "]";
	}
}
