package fusikun.com.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.Role;
import fusikun.com.api.model.User;
import fusikun.com.api.utils.ConstantErrorCodes;

public class UserRequest {
	private Long id;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	private String username;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotBlank(message = ConstantErrorCodes.NOT_BLANK)
	@Size(min = 8, message = ConstantErrorCodes.NOT_BELOW_8_LETTER)
	private String password;

	@Email(message = ConstantErrorCodes.NOT_MAIL)
	private String email;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	private Long roleId;

	private Boolean isActive;

	public User getUser() {
		User user = new User();
		user.setId(this.id);
		user.setUsername(this.username);
		user.setEmail(this.email);
		user.setIsActive(this.isActive);
		user.setPassword(this.password);
		Role role = new Role();
		role.setId(this.roleId);
		user.setRole(role);
		return user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}
