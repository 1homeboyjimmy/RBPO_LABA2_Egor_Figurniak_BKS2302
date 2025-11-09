package example.service;

import example.entity.Warehouse;
import example.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepo;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepo.findAll();
    }

    public Warehouse getWarehouseById(Long id) {
        return warehouseRepo.findById(id).orElse(null);
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepo.save(warehouse);
    }

    public Warehouse updateWarehouse(Long id, Warehouse updatedWarehouse) {
        if (warehouseRepo.existsById(id)) {
            updatedWarehouse.setId(id);
            return warehouseRepo.save(updatedWarehouse);
        }
        return null;
    }

    public void deleteWarehouse(Long id) {
        warehouseRepo.deleteById(id);
    }
}