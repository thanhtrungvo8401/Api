package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import fusikun.com.api.model.app.Auth;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}
