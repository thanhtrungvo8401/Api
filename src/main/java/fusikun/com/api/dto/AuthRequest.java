package fusikun.com.api.dto;

import javax.validation.constraints.NotNull;

import fusikun.com.api.model.Auth;
import fusikun.com.api.utils.ConstantErrorCodes;

public class AuthRequest {
	Long id;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	Boolean isActive;

	Auth getAuth() {
		Auth auth = new Auth();
		auth.setIsActive(this.isActive);
		auth.setId(this.id);
		return auth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
		
}
