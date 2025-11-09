package example.controller;

import example.entity.Stock;
import example.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock created = stockService.createStock(stock);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock updatedStock) {
        Stock updated = stockService.updateStock(id, updatedStock);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/move")
    public ResponseEntity<String> moveItem(
            @RequestParam Long fromWarehouseId,
            @RequestParam Long toWarehouseId,
            @RequestParam Long itemId,
            @RequestParam int quantity) {

        boolean success = stockService.moveItem(fromWarehouseId, toWarehouseId, itemId, quantity);
        if (success) {
            return ResponseEntity.ok("Товар успешно перемещён");
        } else {
            return ResponseEntity.badRequest().body("Ошибка: недостаточно товара или неверные данные");
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveItem(
            @RequestParam Long warehouseId,
            @RequestParam Long itemId,
            @RequestParam int quantity) {

        boolean success = stockService.reserveItem(warehouseId, itemId, quantity);
        if (success) {
            return ResponseEntity.ok("Товар успешно зарезервирован");
        } else {
            return ResponseEntity.badRequest().body("Ошибка: недостаточно товара или неверные данные");
        }
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Stock>> getStocksByWarehouse(@PathVariable Long warehouseId) {
        List<Stock> stocks = stockService.getStocksByWarehouse(warehouseId);
        if (!stocks.isEmpty()) {
            return ResponseEntity.ok(stocks);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<Stock>> getLowStockItems(@RequestParam int threshold) {
        List<Stock> lowStock = stockService.getLowStockItems(threshold);
        return ResponseEntity.ok(lowStock);
    }

    @GetMapping("/check-threshold")
    public ResponseEntity<Boolean> isBelowThreshold(
            @RequestParam Long warehouseId,
            @RequestParam Long itemId,
            @RequestParam int threshold) {

        boolean isBelow = stockService.isBelowThreshold(warehouseId, itemId, threshold);
        return ResponseEntity.ok(isBelow);
    }
}