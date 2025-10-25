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

    // GET /api/stocks — получить все остатки
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAllStocks();
    }

    // GET /api/stocks/{id} — получить один остаток
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/stocks — создать остаток
    @PostMapping
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock created = stockService.createStock(stock);
        return ResponseEntity.ok(created);
    }

    // PUT /api/stocks/{id} — обновить остаток
    @PutMapping("/{id}")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock updatedStock) {
        Stock updated = stockService.updateStock(id, updatedStock);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/stocks/{id} — удалить остаток
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/stocks/move — переместить товар
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
}