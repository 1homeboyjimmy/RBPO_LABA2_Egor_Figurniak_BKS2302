package example.repository;

import example.model.UserSession;
import example.model.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByRefreshToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE UserSession s SET s.status = :status WHERE s.refreshToken = :token")
    void invalidateSession(@Param("token") String token, @Param("status") SessionStatus status);
}