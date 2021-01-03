package fusikun.com.api.interceptors;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fusikun.com.api.model.JwtUserDetails;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.IgnoreUrl;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Ignore some URL:
		String reqUrl = request.getRequestURI();
		if (IgnoreUrl.listUrl.contains(reqUrl)) {
			return true;
		}
		JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		// Check ADMIN role:
		String roleName = jwtUserDetails.getRole().getRoleName();
		if (roleName.equals(Constant.ADMIN_ROLE)) {
			return true;
		}
		List<String> menuRegexs = jwtUserDetails.getMenus().stream().map(menu -> menu.getRegex())
				.collect(Collectors.toList());
		for (String regex : menuRegexs) {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(reqUrl);
			if (m.find()) {
				return true;
			}
		}
		throw new AccessDeniedException("Your access to '" + reqUrl + "' is forbidden");
	}
}
