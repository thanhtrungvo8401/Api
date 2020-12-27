package fusikun.com.api.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Boolean isActive;
	
	private String url;
	
	private String name;

	private Date createdDate;

	private Date updatedDate;

	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Auth> auths;

	@OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Menu> menus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	private Menu parentMenu;

	
	public Menu() {
	}
	
	public Menu(String url, String name) {
		super();
		this.url = url;
		this.name = name;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public List<Auth> getAuths() {
		return auths;
	}

	public void setAuths(List<Auth> auths) {
		this.auths = auths;
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

	@Override
	public String toString() {
		return "Menu [id=" + id + ", isActive=" + isActive + ", url=" + url + ", name=" + name + ", createdDate="
				+ createdDate + ", updatedDate=" + updatedDate + ", auths=" + auths + ", menus=" + menus
				+ ", parentMenu=" + parentMenu + "]";
	}
}
