package fusikun.com.api.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fusikun.com.api.model.app.Menu;
import fusikun.com.api.utils.ConstantErrorCodes;

public class MenuRequest {
	private UUID id;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	private String url;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	private String name;

	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	private String regex;

	private UUID parentId;

	public Menu getMenu() {
		Menu menu = new Menu();
		menu.setId(this.id);
		menu.setUrl(this.url);
		menu.setName(this.name);
		menu.setRegex(this.regex);
		if (this.parentId != null) {
			Menu parentMenu = new Menu();
			parentMenu.setId(this.parentId);
			menu.setParentMenu(parentMenu);
		}
		return menu;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getParentId() {
		return parentId;
	}

	public void setParentId(UUID parentId) {
		this.parentId = parentId;
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

}
