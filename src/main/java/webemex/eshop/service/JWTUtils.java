package webemex.eshop.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class provides utility functions for handling JSON Web Tokens (JWT).
 * It includes methods for generating tokens, refreshing tokens, extracting claims,
 * and validating token expiry.
 */
@Component
public class JWTUtils {

    private SecretKey key;
    private static final long EXPIRATION_TIME = 86400000;  // 24 hours

    /**
     * Constructor for JWTUtils.
     * Initializes the secret key used for signing JWT tokens.
     */
    public JWTUtils() {
        String secretKey = "F4D9E9D7C7A45E58F17B23CA19F8E8E55A7F3726984B425DA6E1CB63E33FD0D3";
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /**
     * Generates a JWT token for a given user.
     *
     * @param userDetails The UserDetails object representing the authenticated user.
     * @return A JWT token.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * Generates a JWT refresh token with additional claims.
     *
     * @param claims      Additional claims to include in the token.
     * @param userDetails The UserDetails object representing the authenticated user.
     * @return A JWT refresh token.
     */
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token The JWT token.
     * @return The extracted username.
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from a JWT token.
     *
     * @param <T> The type of the extracted claim.
     * @param token The JWT token.
     * @param claimsTFunction A function that specifies which claim to extract.
     * @return The extracted claim.
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(
                Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload()
        );
    }

    /**
     * Checks if a JWT token is valid and matches the given user.
     *
     * @param token The JWT token.
     * @param userDetails The UserDetails object representing the authenticated user.
     * @return True if the token is valid and not expired, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if a JWT token has expired.
     *
     * @param token The JWT token.
     * @return True if the token has expired, false otherwise.
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
