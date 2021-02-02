package fusikun.com.api.model.app;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "menu")
public class Menu {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "id", columnDefinition = "BINARY(16)")
	private UUID id;

	private Boolean isActive;

	private String url;

	private String regex;

	private String name;

	private String method;

	private Date createdDate;

	private Date updatedDate;

	@OneToMany(mappedBy = "menu", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Auth> auths;

	@OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Menu> menus;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId")
	private Menu parentMenu;

	public Menu() {
	}

	public Menu(String url, String name, String regex, String method) {
		super();
		this.url = url;
		this.name = name;
		this.regex = regex;
		this.method = method;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
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
		return "Menu [id=" + id + ", isActive=" + isActive + ", url=" + url + ", regex=" + regex + ", name=" + name
				+ ", method=" + method + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", auths="
				+ auths + ", menus=" + menus + ", parentMenu=" + parentMenu + "]";
	}
}
