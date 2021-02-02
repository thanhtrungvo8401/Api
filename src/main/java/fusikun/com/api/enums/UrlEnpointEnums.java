package fusikun.com.api.enums;

public enum UrlEnpointEnums {
	SETVOCAS("/set-vocas", "^\\/set-vocas$", ""),
	SETVOCAS_create("/set-vocas", "^\\/set-vocas$", "POST"),
	SETVOCAS_GetByAuthorId("/users/{authorId}/set-vocas", "^\\/users\\/[1-9a-z-]{1,}\\/set-vocas$", "GET"),
	
	VOCABULARIES("/vocabularies", "^\\/vocabularies$", "GET"),
	VOCABULARIES_create("/vocabularies", "^\\/vocabularies", "POST"),
	VOCABULARIES_getBySetVocasId("/set-vocas/{id}/vocas", "^\\/set-vocas\\/[1-9a-z-]{1,}\\/vocas$", "GET"),

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
