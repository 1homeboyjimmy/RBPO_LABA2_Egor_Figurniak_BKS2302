package example.auth;

import example.entity.User;
import example.model.UserSession;
import example.model.enums.SessionStatus;
import example.repository.UserSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthSessionService {

    private final UserSessionRepository userSessionRepository;

    public AuthSessionService(UserSessionRepository userSessionRepository) {
        this.userSessionRepository = userSessionRepository;
    }

    @Transactional
    public UserSession createSession(User user, String refreshToken, long refreshExpirationMs) {
        UserSession session = new UserSession();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setStatus(SessionStatus.ACTIVE);
        session.setCreatedAt(LocalDateTime.now());
        session.setExpiresAt(LocalDateTime.now().plusSeconds(refreshExpirationMs / 1000));

        return userSessionRepository.save(session);
    }

    @Transactional
    public boolean revokeSession(String refreshToken) {
        return userSessionRepository.findByRefreshToken(refreshToken)
                .map(session -> {
                    session.setStatus(SessionStatus.REVOKED);
                    userSessionRepository.save(session);
                    return true;
                })
                .orElse(false);
    }

    public boolean isSessionValid(String refreshToken) {
        return userSessionRepository.findByRefreshToken(refreshToken)
                .map(session -> session.getStatus() == SessionStatus.ACTIVE)
                .orElse(false);
    }
}