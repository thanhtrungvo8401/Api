package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.study.SetVoca;
import fusikun.com.api.model.study.Voca;
import fusikun.com.api.utils.ConstantErrorCodes;

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
	private String kanji;

	@Size(max = 255, message = ConstantErrorCodes.NOT_OVER_255_LETTER)
	private String sentence;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	private UUID setId;

	public Voca getVocaObject() {
		Voca vocaObject = new Voca();
		vocaObject.setId(id);
		vocaObject.setVoca(this.voca);
		vocaObject.setMeaning(meaning);
		vocaObject.setKanji(kanji);
		vocaObject.setSentence(sentence);

		SetVoca setVoca = new SetVoca();
		setVoca.setId(setId);
		vocaObject.setSetVoca(setVoca);
		return vocaObject;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getVoca() {
		return voca;
	}

	public void setVoca(String voca) {
		this.voca = voca;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getKanji() {
		return kanji;
	}

	public void setKanji(String kanji) {
		this.kanji = kanji;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public UUID getSetId() {
		return setId;
	}

	public void setSetId(UUID setId) {
		this.setId = setId;
	}

}
