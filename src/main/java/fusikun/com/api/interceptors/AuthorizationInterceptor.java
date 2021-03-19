package fusikun.com.api.interceptors;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.model.app.Menu;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.IgnoreUrl;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String reqUrl = request.getRequestURI();
		String method = request.getMethod();
		if (IgnoreUrl.isPublicUrl(request)) {
			return true;
		}
		// Check permissions
		JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		// Check ADMIN role:
		String roleName = jwtUserDetails.getRole().getRoleName();
		if (roleName.equals(Constant.ADMIN_ROLE)) {
			return true;
		}
		if (roleName.equals(Constant.STUDENT_ROLE)) {
			throw new AccessDeniedException("Your access to '" + reqUrl + " & method=" + method + "' is forbidden");
		}

		List<Menu> menus = jwtUserDetails.getMenus();
		for (Menu menu : menus) {
			Pattern p = Pattern.compile(menu.getRegex());
			Matcher m = p.matcher(reqUrl);
			if (m.find() && menu.getMethod().equals(method)) {
				return true;
			}
		}
		throw new AccessDeniedException("Your access to '" + reqUrl + " & method=" + method + "' is forbidden");
	}
}
