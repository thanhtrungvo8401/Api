package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fusikun.com.api.model.app.User;
import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.utils.ConstantErrorCodes;

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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public UUID getAuthorId() {
		return authorId;
	}

	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	public Integer getMaxVoca() {
		return maxVoca;
	}

	public Integer getTotalVocas() {
		return totalVocas;
	}

	public void setTotalVocas(Integer totalVocas) {
		this.totalVocas = totalVocas;
	}

}
