package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.app.Role;
import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.Center;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private UUID id;

    @Size(min = 8, message = ConstantErrorCodes.NOT_BELOW_8_LETTER)
    private String password;

    @Email(message = ConstantErrorCodes.NOT_MAIL)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private String email;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private UUID roleId;

    private int maxSetVocas;

    private UUID centerId;

    private Boolean isActive;

    public User getUser() {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setIsActive(this.isActive);
        user.setPassword(this.password);
        user.setMaxSetVocas(this.maxSetVocas);
        user.setRole(new Role(this.roleId));
        if (this.centerId != null) {
            user.setCenter(new Center(this.centerId));
        }
        return user;
    }
}
