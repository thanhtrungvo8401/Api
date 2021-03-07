package fusikun.com.api.dto;

import java.util.Date;
import java.util.UUID;

import fusikun.com.api.model.study.Voca;

public class VocaResponse {
	private UUID id;
	private String voca;
	private String note;
	private String sentence;
	private Date createdDate;
	private Date updatedDate;
	private UUID setId;

	public VocaResponse() {
	}

	public VocaResponse(Voca voca) {
		this.id = voca.getId();
		this.voca = voca.getVoca();
		this.note = voca.getNote();
		this.sentence = voca.getSentence();
		this.createdDate = voca.getCreatedDate();
		this.updatedDate = voca.getUpdatedDate();
		this.setId = voca.getSetVoca().getId();
	}

	public UUID getSetId() {
		return setId;
	}

	public void setSetId(UUID setId) {
		this.setId = setId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}
