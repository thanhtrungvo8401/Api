package fusikun.com.api.enums;

public enum UrlEnpointEnums {

	MENU_ACTIONS("/menu-actions"), 
	MENU_ACTIONS__GET_DEFINED_URL("/menu-actions/defined-url"),
	MENU_ACTIONS__GENERATE("/menu-actions/generate"),
	
	ROLES("/roles"),
	ROLES__CREATE("/create"),
	
	VOCABULARIES("/vocabularies"),
	
	AUTHENTICATE("/authenticate"),
	
	AUTHENTICATE__LOGIN("/authenticate/login"),
	
	AUTHENTICATE__LOGOUT("/authenticate/logout")
	;

	private final String url;

	UrlEnpointEnums(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
