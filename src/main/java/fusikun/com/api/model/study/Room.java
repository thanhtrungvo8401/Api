package fusikun.com.api.model.study;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import fusikun.com.api.model.app.User;

public class Room {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "centerId", nullable = true)
	private Center center;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "room_teachers", joinColumns = @JoinColumn(name = "roomId"), inverseJoinColumns = @JoinColumn(name = "teacherId"))
	private List<User> teachers;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "room_members", joinColumns = @JoinColumn(name = "roomId"), inverseJoinColumns = @JoinColumn(name = "memberId"))
	private List<User> members;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "room_setVocas", joinColumns = @JoinColumn(name = "roomId"), inverseJoinColumns = @JoinColumn(name = "setVocaId"))
	private List<SetVoca> setVocas;

	private String roomName;

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public List<SetVoca> getSetVocas() {
		return setVocas;
	}

	public void setSetVocas(List<SetVoca> setVocas) {
		this.setVocas = setVocas;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<User> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<User> teachers) {
		this.teachers = teachers;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

}
