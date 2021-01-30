package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.app.Role;
import fusikun.com.api.model.app.User;
import fusikun.com.api.utils.ConstantErrorCodes;

public class UserRequest {
	private UUID id;

	@Size(min = 8, message = ConstantErrorCodes.NOT_BELOW_8_LETTER)
	private String password;

	@Email(message = ConstantErrorCodes.NOT_MAIL)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	private String email;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	private UUID roleId;

	private Boolean isActive;

	public User getUser() {
		User user = new User();
		user.setId(this.id);
		user.setEmail(this.email);
		user.setIsActive(this.isActive);
		user.setPassword(this.password);
		Role role = new Role();
		role.setId(this.roleId);
		user.setRole(role);
		return user;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UUID getRoleId() {
		return roleId;
	}

	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
