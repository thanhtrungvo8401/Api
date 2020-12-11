package fusikun.com.api.requestFilters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import fusikun.com.api.exceptionHandlers.InvalidTokenException;
import fusikun.com.api.service.JwtUserDetailsService;
import fusikun.com.api.utils.AuthContants;
import fusikun.com.api.utils.ConstantErrorMessages;
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
		if (IgnoreUrl.listUrl.contains(reqUrl.substring(IgnoreUrl.prefixRemove))) {
			chain.doFilter(request, response);
			return;
		}
		// URL not in Ignore list => validate
		final String authorizationHeader = request.getHeader(AuthContants.AUTHORIZATION);
		String username = null;
		String jwt = null;
		if (authorizationHeader != null && authorizationHeader.startsWith(AuthContants.BEARER)) {
			jwt = authorizationHeader.substring(AuthContants.BEARER_INDEX);
			username = jwtTokenUtil.getUserNameFromToken(jwt);
		} else {
			throw new InvalidTokenException(ConstantErrorMessages.INVALID_TOKEN);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			} else
				throw new InvalidTokenException(ConstantErrorMessages.INVALID_TOKEN);
		} else {
			throw new InvalidTokenException(ConstantErrorMessages.INVALID_TOKEN);
		}
		chain.doFilter(request, response);
	}
}
