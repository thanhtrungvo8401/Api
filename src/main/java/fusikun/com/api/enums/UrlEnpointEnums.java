package fusikun.com.api.enums;

public enum UrlEnpointEnums {

	MENU_ACTIONS("/menu-actions"), 
	MENU_ACTIONS__GET_DEFINED_URL("/menu-actions/defined-url"),
	MENU_ACTIONS__GENERATE("/menu-actions/generate"),
	MENU_ACTIONS__COUNT("/menu-actions/count"),
	
	ROLES("/roles"),
	
	VOCABULARIES("/vocabularies"),
	
	AUTHENTICATE("/authenticate"),
	
	AUTHENTICATE_LOGIN("/authenticate/login"),
	
	AUTHENTICATE_LOGOUT("/authenticate/logout")
	;

	private final String url;

	UrlEnpointEnums(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
