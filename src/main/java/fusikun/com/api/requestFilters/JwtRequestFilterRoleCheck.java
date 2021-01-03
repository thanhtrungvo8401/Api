package fusikun.com.api.requestFilters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fusikun.com.api.model.JwtUserDetails;
import fusikun.com.api.utils.IgnoreUrl;

@Component
public class JwtRequestFilterRoleCheck extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Ignore some URL:
		String reqUrl = request.getRequestURI();
		if (IgnoreUrl.listUrl.contains(reqUrl)) {
			chain.doFilter(request, response);
			return;
		}
		JwtUserDetails jwtUserDetails = (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<String> menuUrl = jwtUserDetails.getMenus().stream().map(menu -> menu.getUrl())
				.collect(Collectors.toList());
		System.out.println(menuUrl);
		// DEFINED REGULAR EXRESSSION
		chain.doFilter(request, response);
	}

}
