package fusikun.com.api.model.study;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import fusikun.com.api.model.app.User;

@Entity
@Table(name = "set_voca")
public class SetVoca {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

	private String setName;
	private Integer maxVoca;
	private Integer totalVocas;
	private Boolean isActive;
	private Date createdDate;
	private Date updatedDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorId", nullable = false)
	private User author;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "setVoca", cascade = CascadeType.REMOVE)
	private List<Voca> vocas;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "setVocas")
	private List<Room> rooms;

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public Integer getMaxVoca() {
		return maxVoca;
	}

	public void setMaxVoca(Integer maxVoca) {
		this.maxVoca = maxVoca;
	}

	public Integer getTotalVocas() {
		return totalVocas;
	}

	public void setTotalVocas(Integer totalVocas) {
		this.totalVocas = totalVocas;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Voca> getVocas() {
		return vocas;
	}

	public void setVocas(List<Voca> vocas) {
		this.vocas = vocas;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
