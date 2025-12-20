package example.auth;

import example.entity.User;
import example.model.enums.SessionStatus;
import example.repository.UserSessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    private final SecretKey jwtSecret;
    private final long jwtAccessExpirationMs;
    private final long jwtRefreshExpirationMs;
    private final UserSessionRepository userSessionRepository;

    public JwtTokenProvider(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.access-expiration-ms}") long jwtAccessExpirationMs,
            @Value("${app.jwt.refresh-expiration-ms}") long jwtRefreshExpirationMs,
            UserSessionRepository userSessionRepository) {
        this.jwtSecret = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtAccessExpirationMs = jwtAccessExpirationMs;
        this.jwtRefreshExpirationMs = jwtRefreshExpirationMs;
        this.userSessionRepository = userSessionRepository;
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(jwtAccessExpirationMs, ChronoUnit.MILLIS)))
                .claim("roles", user.getRole())
                .signWith(jwtSecret)
                .compact();
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();
        Date expiryDate = Date.from(now.toInstant().plus(jwtRefreshExpirationMs, ChronoUnit.MILLIS));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            markSessionAsExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
        }
        return false;
    }

    private void markSessionAsExpired(String token) {
        userSessionRepository.findByRefreshToken(token)
                .ifPresent(session -> {
                    session.setStatus(SessionStatus.EXPIRED);
                    userSessionRepository.save(session);
                });
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody();
    }
}