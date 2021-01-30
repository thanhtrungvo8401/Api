package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import fusikun.com.api.model.app.Auth;
import fusikun.com.api.utils.ConstantErrorCodes;

public class AuthRequest {
	UUID id;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	Boolean isActive;

	Auth getAuth() {
		Auth auth = new Auth();
		auth.setIsActive(this.isActive);
		auth.setId(this.id);
		return auth;
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

}
