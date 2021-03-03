package fusikun.com.api.enums;

public enum UrlEnpointEnums {
	MYPROFILE("/my-profile", "^\\/my-profile", "GET"),
	
	SETVOCAS("/set-vocas", "^\\/set-vocas$", ""),
	SETVOCAS_create("/set-vocas", "^\\/set-vocas$", "POST"),
	SETVOCAS_update("/set-vocas/{id}", "^\\/set-vocas\\/[0-9a-z-]{1,}$", "PUT"),
	SETVOCAS_delete("/set-vocas/{id}", "^\\/set-vocas\\/[0-9a-z-]{1,}$", "DELETE"),
	SETVOCAS_getByAuthorId("/users/{authorId}/set-vocas", "^\\/users\\/[0-9a-z-]{1,}\\/set-vocas$", "GET"),
	
	VOCAS("/vocas", "^\\/vocas$", ""),
	VOCAS_create("/vocas", "^\\/vocas$", "POST"),
	VOCAS_update("/vocas/{id}", "^\\/vocas\\/[0-9a-z-]{1,}$", "PUT"),
	VOCAS_delete("/vocas/{id}", "^\\/vocas\\/[0-9a-z-]{1,}$", "DELETE"),
	VOCAS_getBySetId("/set-vocas/{id}/vocas", "^\\/set-vocas\\/[0-9a-z-]{1,}\\/vocas$", "GET"),

	AUTHENTICATE("/authenticate", "^\\/authenticate$", ""),
	AUTHENTICATE_login("/authenticate/login", "^\\/authenticate\\/login$", "POST"),
	AUTHENTICATE_logout("/authenticate/logout", "^\\/authenticate\\/logout$", "POST")
	;

	private final String url;
	private final String regex;
	private final String method;

	UrlEnpointEnums(String url, String regex, String method) {
		this.url = url;
		this.regex = regex;
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public String getRegex() {
		return regex;
	}

	public String getMethod() {
		return method;
	}
}
