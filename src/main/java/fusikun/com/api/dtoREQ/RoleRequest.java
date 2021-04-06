package fusikun.com.api.dtoREQ;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.app.Role;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {
    private UUID id;

    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String roleName;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String description;

    private Boolean isActive;

    public Role getRole() {
        Role role = new Role();
        role.setId(this.id);
        role.setRoleName(this.roleName);
        role.setDescription(this.description);
        role.setIsActive(this.isActive);
        return role;
    }

    @Override
    public String toString() {
        return "RoleRequest [id=" + id + ", roleName=" + roleName + ", description=" + description + ", isActive="
                + isActive + "]";
    }
}
