package fusikun.com.api.requestFilters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fusikun.com.api.exceptionHandlers.InvalidTokenException;
import fusikun.com.api.model.JwtUserDetails;
import fusikun.com.api.service.JwtUserDetailsService;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.ConstantMessages;
import fusikun.com.api.utils.IgnoreUrl;
import fusikun.com.api.utils.JwtTokenUtil;

@Component
public class JwtRequestFilterTokenCheck extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		// Ignore some URL:
		String reqUrl = request.getRequestURI();
		if (IgnoreUrl.listUrl.contains(reqUrl)) {
			chain.doFilter(request, response);
			return;
		}
		// URL not in Ignore list => validate
		final String authorizationHeader = request.getHeader(Constant.AUTH_AUTHORIZATION);
		String username = null;
		String jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith(Constant.AUTH_BEARER)) {
			jwt = authorizationHeader.substring(Constant.AUTH_BEARER_INDEX);
			username = jwtTokenUtil.getUserNameFromToken(jwt);
		} else {
			System.out.println("======= JWT does not start with 'bearer' OR NULL =======");
			throw new InvalidTokenException(ConstantMessages.INVALID_TOKEN);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else {
				System.out.println("======= invalid TOKEN =======");
				throw new InvalidTokenException(ConstantMessages.INVALID_TOKEN);
			}
		} else {
			throw new InvalidTokenException(ConstantMessages.INVALID_TOKEN);
		}
		chain.doFilter(request, response);
	}
}
