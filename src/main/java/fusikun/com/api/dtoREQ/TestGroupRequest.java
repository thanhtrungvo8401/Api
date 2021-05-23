package fusikun.com.api.dtoREQ;

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
    private String myVoca;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String n5;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String n4;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String n3;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String n2;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String n1;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private Integer number;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private UUID ownerId;

    private Boolean isActive;

    public TestGroup getTestGroup() {
        TestGroup testGroup = new TestGroup();
        testGroup.setId(this.id);
        testGroup.setMyVoca(this.myVoca);
        testGroup.setN1(this.n1);
        testGroup.setN2(this.n2);
        testGroup.setN3(this.n3);
        testGroup.setN4(this.n4);
        testGroup.setN5(this.n5);
        testGroup.setNumber(this.number);
        testGroup.setOwner(new User(ownerId));
        testGroup.setIsActive(this.isActive);
        return testGroup;
    }

    public TestGroupRequest(UUID ownerId, Integer number,String myVoca, String n5, String n4,
                            String n3,
                            String n2,
                            String n1) {
        this.ownerId = ownerId;
        this.number = number;
        this.myVoca = myVoca;
        this.n5 = n5; this.n4 = n4;
        this.n3 = n3; this.n2 = n2;
        this.n1 = n1;
    }
}
