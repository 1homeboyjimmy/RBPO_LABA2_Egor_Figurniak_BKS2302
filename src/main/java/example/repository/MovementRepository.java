package example.repository;

import example.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {}