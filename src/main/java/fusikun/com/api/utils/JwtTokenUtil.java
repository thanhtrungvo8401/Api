package fusikun.com.api.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import fusikun.com.api.model.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
@PropertySource(value = "classpath:jwt.properties")
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = 7358756538128482919L;
	@Value("${jwt.validity.hours}")
	private Double JWT_TOKEN_HOURS = 0.0;
	@Value("${jwt.secret.key}")
	private String SECRET_KEY;

	// retrieve user from jwt-token:
	public String getUserNameFromToken(String token) {
		return getClaimsFromToken(token, Claims::getSubject);
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
		Map<String, Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims, userDetails);
	}

	// while creating the token
	// 1) define claims of the token, like Issuer, Expiration, Subject, and ID
	// 2) Sign the JWT using the HS512 algorithm and secret key.
	// 3) According to JWS Compact Serialization
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, JwtUserDetails userDetails) {
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getUsername())
				.setId(userDetails.getAccessToken())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Math.round(JWT_TOKEN_HOURS * 60 * 60 * 1000)))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, JwtUserDetails userDetails) {
		final String username = getUserNameFromToken(token);
		final String accessToken = getAccessTokenFromToken(token);
		return (username.equals(userDetails.getUsername()) && accessToken.equals(userDetails.getAccessToken())
				&& !isTokenExpired(token));
	}
}
