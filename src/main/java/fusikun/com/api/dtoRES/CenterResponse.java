package fusikun.com.api.dtoRES;

import fusikun.com.api.model.study.Center;
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
public class CenterResponse {
    private UUID id;
    private String centerName;
    private Date createdDate;
    private Boolean isActive;
    private Date updatedDate;

    public CenterResponse(Center center) {
        this.id = center.getId();
        this.centerName = center.getCenterName();
        this.createdDate = center.getCreatedDate();
        this.updatedDate = center.getUpdatedDate();
        this.isActive = center.getIsActive();
    }
}
