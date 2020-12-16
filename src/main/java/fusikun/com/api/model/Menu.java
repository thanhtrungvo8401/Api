package fusikun.com.api.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import fusikun.com.api.utils.ConstantErrorCodes;


@Entity
@Table(name = "menu")
public class Menu {
	@Id
	private long id;
		
	private Boolean isActive;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@NotBlank(message = ConstantErrorCodes.NOT_BLANK)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
//	@UniqueElements(message = ConstantErrorCodes.UNIQUE_VALUE)
	private String url;
	
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@NotBlank(message = ConstantErrorCodes.NOT_BLANK)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
//	@UniqueElements(message = ConstantErrorCodes.UNIQUE_VALUE)
	private String name;

	private Date createDate;

	private Date updateDate;

	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Auth> auths = new HashSet<>();

	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Menu> menus = new HashSet<>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "parentId")
	private Menu menu;
	
	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Set<Auth> getAuths() {
		return auths;
	}

	public void setAuths(Set<Auth> auths) {
		this.auths = auths;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
