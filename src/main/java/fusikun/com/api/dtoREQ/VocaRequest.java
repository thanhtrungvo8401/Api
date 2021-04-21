package fusikun.com.api.dtoREQ;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.model.study.Voca;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VocaRequest {
    private UUID id;

    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String voca;

    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String meaning;

    @Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
    private String note;

    @Size(max = 255, message = ConstantErrorCodes.NOT_OVER_255_LETTER)
    private String sentence;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private UUID setId;

    private Integer code;

    public Voca getVocaObject() {
        Voca vocaObject = new Voca();
        vocaObject.setId(id);
        vocaObject.setVoca(this.voca);
        vocaObject.setMeaning(meaning);
        vocaObject.setNote(note);
        vocaObject.setSentence(sentence);
        vocaObject.setCode(code);

        SetVoca setVoca = new SetVoca();
        setVoca.setId(setId);
        vocaObject.setSetVoca(setVoca);
        return vocaObject;
    }
}
