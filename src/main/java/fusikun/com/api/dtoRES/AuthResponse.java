package fusikun.com.api.dtoRES;

import java.util.UUID;

import fusikun.com.api.model.app.Auth;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private UUID id;
    private Boolean isActive;
    private MenuResponse menu;

    public AuthResponse(Auth auth, MenuResponse menu) {
        this.id = auth.getId();
        this.isActive = auth.getIsActive();
        this.menu = menu;
    }

    public AuthResponse(Auth auth) {
        this.id = auth.getId();
        this.isActive = auth.getIsActive();
    }
}
