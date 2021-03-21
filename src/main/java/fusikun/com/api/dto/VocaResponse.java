package fusikun.com.api.dto;

import java.util.Date;
import java.util.UUID;

import fusikun.com.api.model.study.Voca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VocaResponse {
    private UUID id;
    private String voca;
    private String note;
    private String meaning;
    private String sentence;
    private Date createdDate;
    private Date updatedDate;
    private UUID setId;


    public VocaResponse(Voca voca) {
        this.id = voca.getId();
        this.voca = voca.getVoca();
        this.note = voca.getNote();
        this.meaning = voca.getMeaning();
        this.sentence = voca.getSentence();
        this.createdDate = voca.getCreatedDate();
        this.updatedDate = voca.getUpdatedDate();
        this.setId = voca.getSetVoca().getId();
    }

}
