package fusikun.com.api.enums;

public enum UrlEnpointEnums {

	MENU_ACTIONS("/menu-actions"), 
	MENU_ACTIONS__GET_DEFINED_URL("/menu-actions/defined-url"),
	MENU_ACTIONS__GENERATE("/menu-actions/generate"),
	MENU_ACTIONS__COUNT("/menu-actions/count"),
	
	ROLES("/roles"),
	ROLES__CREATE("/roles/create"),
	ROLES__DETAIL("/roles/{id}"),
	ROLES__UPDATE("/roles/{id}/update"),
	ROLES__DELETE("/roles/{id}/delete"),	
	
	MANAGERS("/managers"),
	MANAGERS__CREATE("/managers/create"),
	
	STUDENT("/students"),
	STUDENT__CREATE("/students/create"),
	
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
