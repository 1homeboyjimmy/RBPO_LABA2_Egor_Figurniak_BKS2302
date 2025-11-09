package example.service;

import example.entity.Supplier;
import example.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepo;

    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }

    public Supplier getSupplierById(Long id) {
        return supplierRepo.findById(id).orElse(null);
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {
        if (supplierRepo.existsById(id)) {
            updatedSupplier.setId(id);
            return supplierRepo.save(updatedSupplier);
        }
        return null;
    }

    public void deleteSupplier(Long id) {
        supplierRepo.deleteById(id);
    }
}