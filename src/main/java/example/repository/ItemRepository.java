package example.repository;

import example.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // Spring Data JPA автоматически создаёт реализацию
}