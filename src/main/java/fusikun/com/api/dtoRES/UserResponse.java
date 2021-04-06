package fusikun.com.api.dtoRES;

import java.util.Date;
import java.util.UUID;

import fusikun.com.api.model.app.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private Date createdDate;
    private Date updatedDate;
    private RoleResponse role;
    private CenterResponse center;
    private Integer maxSetVocas;
    private Boolean isActive;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
        this.updatedDate = user.getUpdatedDate();
        this.maxSetVocas = user.getMaxSetVocas();
        this.isActive = user.getIsActive();
        this.role = new RoleResponse(user.getRole());
        this.center = new CenterResponse(user.getCenter());
    }
}
