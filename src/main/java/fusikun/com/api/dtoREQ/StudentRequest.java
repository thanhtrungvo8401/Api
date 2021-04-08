package fusikun.com.api.dtoREQ;

import java.util.UUID;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private UUID id;

    @Size(min = 8, message = ConstantErrorCodes.NOT_BELOW_8_LETTER)
    private String password;

    @Email(message = ConstantErrorCodes.NOT_MAIL)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private String email;

    private UUID roleId;

    private Boolean isActive;

    public UserRequest getUserRequest() {
        UserRequest userRequest = new UserRequest();
        userRequest.setId(id);
        userRequest.setPassword(password);
        userRequest.setEmail(email);
        userRequest.setRoleId(roleId);
        userRequest.setIsActive(isActive);
        return userRequest;
    }

}
