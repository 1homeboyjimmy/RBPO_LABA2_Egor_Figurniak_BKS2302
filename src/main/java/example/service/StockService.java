package example.service;

import example.entity.*;
import example.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    @Autowired
    private WarehouseRepository warehouseRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private MovementRepository movementRepo;

    public List<Stock> getAllStocks() {
        return stockRepo.findAll();
    }

    public Stock getStockById(Long id) {
        Optional<Stock> stockOpt = stockRepo.findById(id);
        return stockOpt.orElse(null);
    }

    public Stock createStock(Stock stock) {
        if (stock.getQuantity() < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }
        return stockRepo.save(stock);
    }

    public Stock updateStock(Long id, Stock updatedStock) {
        Stock existing = stockRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setWarehouse(updatedStock.getWarehouse());
            existing.setItem(updatedStock.getItem());
            existing.setQuantity(updatedStock.getQuantity());
            return stockRepo.save(existing);
        }
        return null;
    }

    public void deleteStock(Long id) {
        stockRepo.deleteById(id);
    }

    @Transactional
    public boolean moveItem(Long fromWarehouseId, Long toWarehouseId, Long itemId, int quantity) {
        // 1. Проверяем, есть ли нужное количество на исходном складе
        Stock fromStock = stockRepo.findByWarehouseIdAndItemId(fromWarehouseId, itemId);
        if (fromStock == null || fromStock.getQuantity() < quantity) {
            return false; // недостаточно товара
        }

        Stock toStock = stockRepo.findByWarehouseIdAndItemId(toWarehouseId, itemId);
        if (toStock == null) {
            toStock = new Stock();
            toStock.setWarehouse(warehouseRepo.findById(toWarehouseId).orElse(null));
            toStock.setItem(itemRepo.findById(itemId).orElse(null));
            toStock.setQuantity(quantity);
        } else {
            toStock.setQuantity(toStock.getQuantity() + quantity);
        }

        fromStock.setQuantity(fromStock.getQuantity() - quantity);

        stockRepo.save(fromStock);
        stockRepo.save(toStock);

        Movement movement = new Movement();
        movement.setFromWarehouse(warehouseRepo.findById(fromWarehouseId).orElse(null));
        movement.setToWarehouse(warehouseRepo.findById(toWarehouseId).orElse(null));
        movement.setItem(itemRepo.findById(itemId).orElse(null));
        movement.setQuantity(quantity);
        movementRepo.save(movement);

        return true;
    }

    @Transactional
    public boolean reserveItem(Long warehouseId, Long itemId, int quantity) {
        Stock stock = stockRepo.findByWarehouseIdAndItemId(warehouseId, itemId);
        if (stock == null || stock.getQuantity() < quantity) {
            return false;
        }
        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepo.save(stock);
        return true;
    }

    public boolean isBelowThreshold(Long warehouseId, Long itemId, int threshold) {
        Stock stock = stockRepo.findByWarehouseIdAndItemId(warehouseId, itemId);
        if (stock == null) return true;
        return stock.getQuantity() < threshold;
    }

    public List<Stock> getStocksByWarehouse(Long warehouseId) {
        return stockRepo.findByWarehouseId(warehouseId);
    }

    public List<Stock> getLowStockItems(int threshold) {
        return stockRepo.findByQuantityLessThan(threshold);
    }
}