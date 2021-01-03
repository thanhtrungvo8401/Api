package fusikun.com.api.dto;

import fusikun.com.api.model.Menu;

public class MenuResponse {
	private Long id;
	private String url;
	private String name;
	private String regex;
	private Long parentId;

	public MenuResponse() {
	}

	public MenuResponse(Menu menu) {
		id = menu.getId();
		url = menu.getUrl();
		name = menu.getName();
		regex = menu.getRegex();
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

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}
