package fusikun.com.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class IgnoreUrl {
	private static final String[] _LIST_PUPLIC_URL = { "/api/common/v1/authenticate/login<!>POST",
			"/api/common/v1/students<!>POST", "/api/v1/initial-project<!>GET" };

	private static final List<String> generateUrlFromListDefined(Boolean hasMethod) {
		if (hasMethod)
			return Arrays.asList(_LIST_PUPLIC_URL);
		else
			return Arrays.asList(_LIST_PUPLIC_URL).stream().map(el -> el.split(Constant.FILTER_DIVICE)[0])
					.collect(Collectors.toList());

	}

	public static final List<String> LIST_PUPLIC_URL = generateUrlFromListDefined(false);

	public static final String[] SWAGGER_WHITELIST = { "/favicon.ico", "/v2/api-docs", "/swagger-resources",
			"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
			"/v3/api-docs/**", "/swagger-ui/**", "/error", "/swagger-ui/index.html" };

	public static final Boolean isPublicUrl(HttpServletRequest request) {
		String ignoreAuthenUrl = request.getRequestURI() + Constant.FILTER_DIVICE + request.getMethod();
		if (generateUrlFromListDefined(true).contains(ignoreAuthenUrl)) {
			return true;
		}
		String reqUrl = request.getRequestURI();
		for (String url : SWAGGER_WHITELIST) {
			if (reqUrl.contains("/swagger-ui") || url.contains(reqUrl) || reqUrl.contains(url)) {
				return true;
			}
		}
		return false;
	}

	public static final Boolean isNotForbiddenUrl(HttpServletRequest request) {
		String reqUrl = request.getRequestURI();
		if (reqUrl.contains(Constant.API_COMMON_URL)) {
			return true;
		}
		return isPublicUrl(request);
	}
}
