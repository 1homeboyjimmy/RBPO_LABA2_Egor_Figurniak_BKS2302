package example.repository;

import example.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {

    // Метод для поиска остатка по складу и товару
    @Query("SELECT s FROM Stock s WHERE s.warehouseId = :warehouseId AND s.itemId = :itemId")
    Stock findByWarehouseIdAndItemId(@Param("warehouseId") Long warehouseId, @Param("itemId") Long itemId);
}