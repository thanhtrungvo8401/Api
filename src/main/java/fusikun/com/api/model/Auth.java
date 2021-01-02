package fusikun.com.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import fusikun.com.api.utils.ConstantErrorCodes;

@Entity
@Table(name = "auth")
public class Auth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean isActive;

	private Date createdDate;

	private Date updatedDate;

	public Auth() {
	}

	public Auth(Role role, Menu menu, Boolean isActive) {
		this.role = role;
		this.menu = menu;
		this.isActive = isActive;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@JoinColumn(name = "roleId")
	private Role role;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@JoinColumn(name = "menuId")
	private Menu menu;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
