package fusikun.com.api.dto;

import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.TestGroup;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGroupRequest {
    private UUID id;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String vocaCodes;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private String corrects;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private UUID ownerId;

    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String name;

    private Boolean isActive;

    public TestGroup getTestGroup() {
        TestGroup testGroup = new TestGroup();
        testGroup.setId(this.id);
        testGroup.setVocaCodes(vocaCodes);
        testGroup.setCorrects(this.corrects);
        testGroup.setName(this.name);
        testGroup.setOwner(new User(ownerId));
        testGroup.setIsActive(this.isActive);
        return testGroup;
    }
}
