package example.service;

import example.entity.Stock;
import example.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepo;

    // Получить все остатки
    public List<Stock> getAllStocks() {
        return stockRepo.findAll();
    }

    // Получить один остаток по ID
    public Stock getStockById(Long id) {
        Optional<Stock> stockOpt = stockRepo.findById(id);
        return stockOpt.orElse(null); // если нет — вернёт null
    }

    // Создать новый остаток
    public Stock createStock(Stock stock) {
        if (stock.getQuantity() < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }
        return stockRepo.save(stock);
    }

    // Обновить остаток
    public Stock updateStock(Long id, Stock updatedStock) {
        Stock existing = stockRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setWarehouseId(updatedStock.getWarehouseId());
            existing.setItemId(updatedStock.getItemId());
            existing.setQuantity(updatedStock.getQuantity());
            return stockRepo.save(existing);
        }
        return null; // не найдено
    }

    // Удалить остаток
    public void deleteStock(Long id) {
        stockRepo.deleteById(id);
    }

    // Перемещение товара между складами
    public boolean moveItem(Long fromWarehouseId, Long toWarehouseId, Long itemId, int quantity) {
        // 1. Проверяем, есть ли нужное количество на исходном складе
        Stock fromStock = stockRepo.findByWarehouseIdAndItemId(fromWarehouseId, itemId);
        if (fromStock == null || fromStock.getQuantity() < quantity) {
            return false; // недостаточно товара
        }

        // 2. Находим или создаём остаток на целевом складе
        Stock toStock = stockRepo.findByWarehouseIdAndItemId(toWarehouseId, itemId);
        if (toStock == null) {
            toStock = new Stock(toWarehouseId, itemId, quantity);
        } else {
            toStock.setQuantity(toStock.getQuantity() + quantity);
        }

        // 3. Уменьшаем количество на исходном складе
        fromStock.setQuantity(fromStock.getQuantity() - quantity);

        // 4. Сохраняем изменения
        stockRepo.save(fromStock);
        stockRepo.save(toStock);

        // 5. Логируем движение (можно добавить сохранение в MovementRepository)
        // Movement movement = new Movement(fromWarehouseId, toWarehouseId, itemId, quantity);
        // movementRepo.save(movement); ← если добавишь movementRepo

        return true;
    }
}