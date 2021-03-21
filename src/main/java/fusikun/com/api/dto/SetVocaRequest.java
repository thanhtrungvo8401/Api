package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.utils.ConstantErrorCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetVocaRequest {
    private UUID id;

    private final Integer maxVoca = 10;
    private Integer totalVocas = 0;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    @NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
    @NotBlank(message = ConstantErrorCodes.NOT_BLANK)
    private String setName;

    @NotNull(message = ConstantErrorCodes.NOT_NULL)
    private UUID authorId;

    public SetVoca getSetVoca() {
        SetVoca setVoca = new SetVoca();
        setVoca.setId(id);
        setVoca.setMaxVoca(maxVoca);
        setVoca.setSetName(setName);
        setVoca.setTotalVocas(totalVocas);

        User author = new User();
        author.setId(authorId);
        setVoca.setAuthor(author);
        return setVoca;
    }
}
