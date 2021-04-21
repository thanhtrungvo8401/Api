package fusikun.com.api.dtoREQ;

import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.RememberGroup;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RememberGroupRequest {
    private UUID id;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String vocaCodes;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String name;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private UUID ownerId;

    private Boolean isActive;

    public RememberGroup getRemembersGroup() {
        RememberGroup rememberGroup = new RememberGroup();
        rememberGroup.setId(this.id);
        rememberGroup.setVocaCodes(vocaCodes);
        rememberGroup.setName(name);
        rememberGroup.setOwner(new User(ownerId));
        rememberGroup.setIsActive(this.isActive);
        return rememberGroup;
    }
}
