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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.utils.ConstantErrorCodes;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
//	@UniqueElements(message = ConstantErrorCodes.UNIQUE_VALUE)
	private String username;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotBlank(message = ConstantErrorCodes.NOT_BLANK)
	private String password;

	@Email(message = ConstantErrorCodes.NOT_MAIL)
	private String email;

	private Boolean isActive;

	private String accessToken;

	private Date createdDate;

	private Date updatedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "roleId")
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	private Role role;

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
