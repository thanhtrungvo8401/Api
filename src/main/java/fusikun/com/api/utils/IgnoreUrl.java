package fusikun.com.api.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

public class IgnoreUrl {
	private static final String[] list = { "/authenticate/login<!>POST", "/students<!>POST", "/initial-project<!>GET" };

	public static final List<String> listUrl(Boolean hasMethod) {
		List<String> listUrlWithMethod = Arrays.asList(list);
		if (hasMethod)
			return listUrlWithMethod;
		else {
			return listUrlWithMethod.stream().map(el -> {
				String url = el.split(Constant.FILTER_DIVICE)[0];
				return url;
			}).collect(Collectors.toList());
		}
	}

	public static final List<String> listUrl = listUrl(false);

	public static final String[] AUTH_WHITELIST = { "/favicon.ico", "/v2/api-docs", "/swagger-resources",
			"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui.html", "/webjars/**",
			"/v3/api-docs/**", "/swagger-ui/**", "/error", "/swagger-ui/index.html" };

	public static final Boolean isPublicUrl(HttpServletRequest request) {
		String ignoreAuthenUrl = request.getRequestURI() + Constant.FILTER_DIVICE + request.getMethod();
		String reqUrl = request.getRequestURI();
		if (listUrl(true).contains(ignoreAuthenUrl)) {
			return true;
		}
		for (int i = 0; i < AUTH_WHITELIST.length; i++) {
			String url = AUTH_WHITELIST[i];
			System.out.println(url + "_" + request.getRequestURI());
			if (url.contains(reqUrl) || reqUrl.contains(url) || url.equals(reqUrl) || reqUrl.contains("/swagger-ui")) {
				return true;
			}
		}
		return false;
	}
}
