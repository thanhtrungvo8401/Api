package fusikun.com.api.dto;

import java.util.UUID;

import fusikun.com.api.model.app.JwtUserDetails;

public class UserInfoObject {
	private UUID id;
	private String roleName;

	public UserInfoObject(JwtUserDetails jwtUserDetails) {
		this.id = jwtUserDetails.getId();
		this.roleName = jwtUserDetails.getRole().getRoleName();
	}

	@SuppressWarnings("unused")
	public UUID getId() {
		return id;
	}

	@SuppressWarnings("unused")
	public String getRoleName() {
		return roleName;
	}
}
