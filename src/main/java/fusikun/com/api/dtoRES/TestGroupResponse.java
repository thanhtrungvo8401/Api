package fusikun.com.api.dtoRES;

import fusikun.com.api.model.study.TestGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestGroupResponse {
    private UUID id;
    private String n1;
    private String n2;
    private String n3;
    private String n4;
    private String n5;
    private String myVoca;
    private Integer number;
    private UUID ownerId;
    private Date createdDate;
    private Date updatedDate;

    public TestGroupResponse(TestGroup testGroup) {
        this.id = testGroup.getId();
        this.n1 = testGroup.getN1();
        this.n2 = testGroup.getN2();
        this.n3 = testGroup.getN3();
        this.n4 = testGroup.getN4();
        this.n5 = testGroup.getN5();
        this.myVoca = testGroup.getMyVoca();
        this.number = testGroup.getNumber();
        this.createdDate = testGroup.getCreatedDate();
        this.updatedDate = testGroup.getUpdatedDate();
        this.ownerId = testGroup.getOwner().getId();
    }
}
