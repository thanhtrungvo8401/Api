package fusikun.com.api.dto;

import java.util.Date;
import java.util.UUID;

import fusikun.com.api.model.study.SetVoca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetVocaResponse {
    private UUID id;
    private Integer totalVocas = 0;
    private String setName;
    private Integer maxVoca;
    private Date createdDate;
    private Date updatedDate;
    private UUID authorId;


    public SetVocaResponse(SetVoca setVoca) {
        this.id = setVoca.getId();
        this.maxVoca = setVoca.getMaxVoca();
        this.createdDate = setVoca.getCreatedDate();
        this.updatedDate = setVoca.getUpdatedDate();
        this.authorId = setVoca.getAuthor().getId();
        this.setName = setVoca.getSetName();
        this.totalVocas = setVoca.getTotalVocas();
    }
}
