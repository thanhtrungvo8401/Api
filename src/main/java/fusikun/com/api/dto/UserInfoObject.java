package fusikun.com.api.dto;

import java.util.UUID;

import fusikun.com.api.model.app.JwtUserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoObject {
    private UUID id;
    private String roleName;

    public UserInfoObject(JwtUserDetails jwtUserDetails) {
        this.id = jwtUserDetails.getId();
        this.roleName = jwtUserDetails.getRole().getRoleName();
    }
}
