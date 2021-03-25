package fusikun.com.api.dto;

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
    private String vocaCodes;
    private String corrects;
    private String name;
    private UUID ownerId;
    private Date createdDate;
    private Date updatedDate;

    public TestGroupResponse(TestGroup testGroup) {
        this.id = testGroup.getId();
        this.vocaCodes = testGroup.getVocaCodes();
        this.corrects = testGroup.getCorrects();
        this.createdDate = testGroup.getCreatedDate();
        this.updatedDate = testGroup.getUpdatedDate();
        this.ownerId = testGroup.getOwner().getId();
        this.name = testGroup.getName();
    }
}
