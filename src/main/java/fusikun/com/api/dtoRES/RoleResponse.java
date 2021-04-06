package fusikun.com.api.dtoRES;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import fusikun.com.api.model.app.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {
    private UUID id;
    private String roleName;
    private String description;
    private Boolean isActive;
    private Date createdDate;
    private Date updatedDate;
    // SETTER ONLY:
    private List<AuthResponse> auths;

    public RoleResponse(Role role) {
        this.id = role.getId();
        this.roleName = role.getRoleName();
        this.description = role.getDescription();
        this.isActive = role.getIsActive();
        this.createdDate = role.getCreatedDate();
        this.updatedDate = role.getUpdatedDate();
    }
}
