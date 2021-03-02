package fusikun.com.api.dto;

import java.util.Date;
import java.util.UUID;

import fusikun.com.api.model.study.SetVoca;

public class SetVocaResponse {
	private UUID id;
	private Integer totalVocas = 0;
	private String setName;
	private Integer maxVoca;
	private Date createdDate;
	private Date updatedDate;
	private UUID authorId;

	public SetVocaResponse() {
	}

	public SetVocaResponse(SetVoca setVoca) {
		this.id = setVoca.getId();
		this.maxVoca = setVoca.getMaxVoca();
		this.createdDate = setVoca.getCreatedDate();
		this.updatedDate = setVoca.getUpdatedDate();
		this.authorId = setVoca.getAuthor().getId();
		this.setName = setVoca.getSetName();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Integer getMaxVoca() {
		return maxVoca;
	}

	public void setMaxVoca(Integer maxVoca) {
		this.maxVoca = maxVoca;
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

	public UUID getAuthorId() {
		return authorId;
	}

	public void setAuthorId(UUID authorId) {
		this.authorId = authorId;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public Integer getTotalVocas() {
		return totalVocas;
	}

	public void setTotalVocas(Integer totalVocas) {
		this.totalVocas = totalVocas;
	}

}
