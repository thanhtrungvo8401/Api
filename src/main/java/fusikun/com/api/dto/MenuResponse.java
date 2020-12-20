package fusikun.com.api.dto;

import java.util.Date;

import fusikun.com.api.model.Menu;

public class MenuResponse {
	private Long id;
	private String url;
	private String name;
	private Long parentId;
	Date createdDate;
	Date updatedDate;

	public MenuResponse() {
	}

	public MenuResponse(Menu menu) {
		id = menu.getId();
		url = menu.getUrl();
		name = menu.getName();
		createdDate = menu.getCreatedDate();
		updatedDate = menu.getUpdatedDate();
		if (menu.getParentMenu() != null) {
			parentId = menu.getParentMenu().getId();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
