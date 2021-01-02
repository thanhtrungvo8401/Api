package fusikun.com.api.dto;

import javax.validation.constraints.NotNull;

import fusikun.com.api.utils.ConstantErrorCodes;

public class AuthRequest {
	Long id;
	
	@NotNull(message = ConstantErrorCodes.NOT_NULL)	
	Boolean isActive;
}
