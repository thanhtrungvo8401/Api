package fusikun.com.api.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import fusikun.com.api.model.Menu;
import fusikun.com.api.utils.ConstantErrorCodes;

public class MenuRequest {
	private Long id;
	
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	private String url;
	
	@NotNull(message = ConstantErrorCodes.NOT_NULL)
	@NotEmpty(message = ConstantErrorCodes.NOT_EMPTY)
	@Size(max = 50, message = ConstantErrorCodes.NOT_OVER_50_LETTER)
	private String name;
	
	private Long parentId;
	
	public Menu getMenu() {
		Menu menu = new Menu();
		menu.setId(this.id);
		menu.setUrl(this.url);
		menu.setName(this.name);
		if (this.parentId != null) {
			Menu parentMenu = new Menu();
			parentMenu.setId(this.parentId);
			menu.setParentMenu(parentMenu);
		}
		return menu;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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
	
	
}
