package fusikun.com.api.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import fusikun.com.api.dto.UserInfoObject;
import fusikun.com.api.model.app.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 7358756538128482919L;
    @Value("${jwt.validity.hours}")
    private Double JWT_TOKEN_HOURS = 0.0;
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    // retrieve user from jwt-token:
    public UserInfoObject getUserInfoFromToken(String token) {
        String userInfo = getClaimsFromToken(token, Claims::getSubject);
        return new Gson().fromJson(userInfo, UserInfoObject.class);
    }

    // retrieve accessToken from jwt-token:
    public String getAccessTokenFromToken(String token) {
        return getClaimsFromToken(token, Claims::getId);
    }

    // retrieve expiration date from jwt-token:
    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieving any information from token we will need the secret key:
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // check if the token has expired:
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user:
    public String generateToken(JwtUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails);
    }

    // while creating the token
    // 1) define claims of the token, like Issuer, Expiration, Subject, and ID
    // 2) Sign the JWT using the HS512 algorithm and secret key.
    // 3) According to JWS Compact Serialization
    // compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, JwtUserDetails userDetails) {
        UserInfoObject userInfo = new UserInfoObject(userDetails);
        String jwt = new Gson().toJson(userInfo);
        return Jwts.builder().setClaims(claims).setSubject(jwt).setId(userDetails.getAccessToken())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Math.round(JWT_TOKEN_HOURS * 60 * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, JwtUserDetails userDetails) {
        UserInfoObject userInfo = getUserInfoFromToken(token);
        final String userId = userInfo.getId().toString();
        final String roleName = userInfo.getRoleName();
        final String accessToken = getAccessTokenFromToken(token);
        return userId.equals(userDetails.getId().toString())
                && accessToken.equals(userDetails.getAccessToken())
                && !isTokenExpired(token)
                && roleName.equals(userDetails.getRole().getRoleName());
    }
}
