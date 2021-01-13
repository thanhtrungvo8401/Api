package fusikun.com.api.dto;

import java.util.Date;
import java.util.UUID;

import fusikun.com.api.model.User;

public class UserResponse {
	private UUID id;
	private String email;
	private Date createdDate;
	private Date updatedDate;
	private RoleResponse role;

	public UserResponse() {
	}

	public UserResponse(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.createdDate = user.getCreatedDate();
		this.updatedDate = user.getUpdatedDate();
		this.role = new RoleResponse(user.getRole());
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public RoleResponse getRole() {
		return role;
	}

	public void setRole(RoleResponse role) {
		this.role = role;
	}

}
