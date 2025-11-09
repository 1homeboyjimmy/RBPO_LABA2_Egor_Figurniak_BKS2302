package example.repository;

import example.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT s FROM Stock s WHERE s.warehouse.id = :warehouseId AND s.item.id = :itemId")
    Stock findByWarehouseIdAndItemId(@Param("warehouseId") Long warehouseId, @Param("itemId") Long itemId);

    List<Stock> findByWarehouseId(Long warehouseId);

    List<Stock> findByQuantityLessThan(int threshold);
}