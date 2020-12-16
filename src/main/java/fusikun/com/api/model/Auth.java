package fusikun.com.api.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private long id;

	private Boolean isActive;

	private Date createDate;

	private Date updateDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@JoinColumn(name = "roleId")
	private Role role;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@JoinColumn(name = "menuId")
	private Menu menu;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
