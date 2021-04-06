package fusikun.com.api.dtoRES;

import fusikun.com.api.model.study.RememberGroup;
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
public class RememberGroupResponse {
    private UUID id;
    private String vocaCodes;
    private String name;
    private String activeCodes;
    private Date createdDate;
    private Date updatedDate;
    private UUID ownerId;

    public RememberGroupResponse(RememberGroup rememberGroup) {
        this.id = rememberGroup.getId();
        this.vocaCodes = rememberGroup.getVocaCodes();
        this.activeCodes = rememberGroup.getActiveCodes();
        this.name = rememberGroup.getName();
        this.createdDate = rememberGroup.getCreatedDate();
        this.updatedDate = rememberGroup.getUpdatedDate();
        this.ownerId = rememberGroup.getOwner().getId();
    }
}
