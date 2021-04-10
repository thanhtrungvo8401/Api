package fusikun.com.api.filters;

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

import fusikun.com.api.dtoRES.UserInfoObject;
import fusikun.com.api.exceptionHandlers.Ex_InvalidTokenException;
import fusikun.com.api.model.app.JwtUserDetails;
import fusikun.com.api.service.JwtUserDetailsService;
import fusikun.com.api.utils.Constant;
import fusikun.com.api.utils.ConstantMessages;
import fusikun.com.api.utils.IgnoreUrl;
import fusikun.com.api.utils.JwtTokenUtil;

@Component
public class AuthenticateFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * When we call
     * -chain.doFilter(request, response);
     * -return;
     * That mean we go to next step and do not authenticate for request and then request
     * will be checked by spring-security to block this request.
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (IgnoreUrl.isPublicUrl(request)) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader(Constant.AUTH_AUTHORIZATION);
        // check exist and valid token format:
        if (authorizationHeader == null || !authorizationHeader.startsWith(Constant.AUTH_BEARER)) {
            System.out.println("======= JWT does not start with 'bearer' OR NULL =======");
            chain.doFilter(request, response);
            return;
        }
        String jwt = authorizationHeader.substring(Constant.AUTH_BEARER_INDEX);
        UserInfoObject userInfo = jwtTokenUtil.getUserInfoFromToken(jwt);

        // check valid token
        if (userInfo == null || userInfo.getId() == null
                || SecurityContextHolder.getContext().getAuthentication() != null) {
            throw new Ex_InvalidTokenException(ConstantMessages.INVALID_TOKEN);
        }
        JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUserInfo(userInfo);

        // check valid token
        if (!jwtTokenUtil.validateToken(jwt, userDetails)) {
            System.out.println("======= invalid TOKEN =======");
            chain.doFilter(request, response);
            return;
        }
        // START TO AUTHENTICATE FOR REQUEST
        UsernamePasswordAuthenticationToken object = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        object.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(object);
        chain.doFilter(request, response);
    }
}
