package fusikun.com.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class IgnoreUrl {
	private static final String[] LIST_PUPLIC_URL = { "/authenticate/login<!>POST", "/students<!>POST",
			"/initial-project<!>GET" };

	public static final List<String> listUrl(Boolean hasMethod) {
		if (hasMethod)
			return Arrays.asList(LIST_PUPLIC_URL);
		else
			return Arrays.asList(LIST_PUPLIC_URL).stream().map(el -> el.split(Constant.FILTER_DIVICE)[0])
					.collect(Collectors.toList());

	}

	public static final List<String> listUrl = listUrl(false);

	public static final String[] AUTH_WHITELIST = { "/favicon.ico", "/v2/api-docs", "/swagger-resources",
			"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
			"/v3/api-docs/**", "/swagger-ui/**", "/error", "/swagger-ui/index.html" };

	public static final Boolean isPublicUrl(HttpServletRequest request) {
		String ignoreAuthenUrl = request.getRequestURI() + Constant.FILTER_DIVICE + request.getMethod();
		if (listUrl(true).contains(ignoreAuthenUrl)) {
			return true;
		}
		String reqUrl = request.getRequestURI();
		for (String url : AUTH_WHITELIST) {
			if (reqUrl.contains("/swagger-ui") || url.contains(reqUrl) || reqUrl.contains(url)) {
				return true;
			}
		}
		return false;
	}
}
