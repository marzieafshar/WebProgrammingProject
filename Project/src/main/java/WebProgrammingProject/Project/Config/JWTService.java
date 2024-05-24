package WebProgrammingProject.Project.Config;

import WebProgrammingProject.Project.Controllers.Requests.TokenRequest;
import WebProgrammingProject.Project.Models.Token;
import WebProgrammingProject.Project.Repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static WebProgrammingProject.Project.Services.AuthenticationService.loggedInUser;

@Service
public class JWTService {

    private static final String SECRET_KEY ="2F413F4428472B4B6250655367566B5970337336763979244226452948404D63";
    public String getUsernameFromToken(Token jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    public <T> T getClaimFromToken(Token jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(jwtToken);
        return claimsResolver.apply(claims);
    }

    public Token generateToken(UserDetails userDetails) {
       return generateToken(new HashMap<>(), userDetails);
    }
    public Token customToken(UserDetails userDetails, TokenRequest request){
        return customToken(new HashMap<>(), userDetails, request);
    }

    public Token customToken(Map<String, Object> claims, UserDetails userDetails, TokenRequest request){
        return new Token(
                Jwts
                        .builder()
                        .setClaims(claims)
                        .setSubject(userDetails.getUsername())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(request.getExpire_date())
                        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                        .compact(), request.getExpire_date());
    }
    public Token generateToken(Map<String, Object> claims, UserDetails userDetails) {
        Date expireDate = new Date(System.currentTimeMillis() + 1000 * 60 * 24);
            Token token = new Token(
                    Jwts
                            .builder()
                            .setClaims(claims)
                            .setSubject(userDetails.getUsername())
                            .setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(expireDate)
                            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                            .compact(), expireDate);
        System.out.println(loggedInUser.getId());
            token.setUserId(loggedInUser.getId());
            return token;

    }

    public boolean isTokenValid(Token jwtToken, UserDetails userDetails) {
        final String username = getUsernameFromToken(jwtToken);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken));
    }

    public boolean isTokenExpired(Token jwtToken) {
        boolean isExpired = getExpirationDateFromToken(jwtToken).before(new Date());
        if(isExpired)
            loggedInUser = null;
        return isExpired;
    }

    private Date getExpirationDateFromToken(Token jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getExpiration);
    }

    private Claims getAllClaimsFromToken(Token jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken.getTokenString())
                .getBody();

    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
