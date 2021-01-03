package fusikun.com.api.enums;

public enum UrlEnpointEnums {

	MENU_ACTIONS("/menu-actions", "^\\/menu-actions$"),
	MENU_ACTIONS__GET_DEFINED_URL("/menu-actions/defined-url", "^\\/menu-actions\\/defined-url$"),
	MENU_ACTIONS__GENERATE("/menu-actions/generate", "^\\/menu-actions\\/generate$"),
	MENU_ACTIONS__COUNT("/menu-actions/count", "^\\/menu-actions\\/count$"),
	
	ROLES("/roles", "^\\/roles$"),
	ROLES__CREATE("/roles/create", "^\\/roles\\/create$"),
	ROLES__DETAIL("/roles/{id}", "^\\/roles\\/\\d{1,}$"),
	ROLES__UPDATE("/roles/{id}/update", "^\\/roles\\/\\d{1,}\\/update$"),
	ROLES__DELETE("/roles/{id}/delete", "^\\/roles\\/\\d{1,}\\/delete$"),
	
	MANAGERS("/managers", "^\\/managers$"),
	MANAGERS__CREATE("/managers/create", "^\\/managers\\/create$"),
	
	STUDENT("/students", "^\\/students$"),
	STUDENT__CREATE("/students/create", "^\\/students\\/create$"),
	
	VOCABULARIES("/vocabularies", "^\\/vocabularies$"),
	
	AUTHENTICATE("/authenticate", "^\\/authenticate$"),
	
	AUTHENTICATE__LOGIN("/authenticate/login", "^\\/authenticate\\/login$"),
	
	AUTHENTICATE__LOGOUT("/authenticate/logout", "^\\/authenticate\\/logout$")
	;

	private final String url;
	private final String regex;

	UrlEnpointEnums(String url, String regex) {
		this.url = url;
		this.regex = regex;
	}

	public String getUrl() {
		return url;
	}
	
	public String getRegex() {
		return regex;
	}
}
