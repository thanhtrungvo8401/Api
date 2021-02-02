package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.utils.ConstantErrorCodes;

public class User_StudentRequest {
	private UUID id;

	@Size(min = 8, message = ConstantErrorCodes.NOT_BELOW_8_LETTER)
	private String password;

	@Email(message = ConstantErrorCodes.NOT_MAIL)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	private String email;

	private UUID roleId;

	private Boolean isActive;

	public UserRequest getUserRequest() {
		UserRequest userRequest = new UserRequest();
		userRequest.setId(id);
		userRequest.setPassword(password);
		userRequest.setEmail(email);
		userRequest.setRoleId(roleId);
		userRequest.setIsActive(isActive);
		return userRequest;
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
