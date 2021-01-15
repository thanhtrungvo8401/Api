package fusikun.com.api.dto;

import java.util.UUID;

import fusikun.com.api.model.app.Menu;

public class MenuResponse {
	private UUID id;
	private String url;
	private String name;
	private String regex;
	private UUID parentId;

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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
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

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
	}
}
