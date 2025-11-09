package example.service;

import example.entity.Item;
import example.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepo;

    public List<Item> getAllItems() {
        return itemRepo.findAll();
    }

    public Item getItemById(Long id) {
        return itemRepo.findById(id).orElse(null);
    }

    public Item createItem(Item item) {
        return itemRepo.save(item);
    }

    public Item updateItem(Long id, Item updatedItem) {
        if (itemRepo.existsById(id)) {
            updatedItem.setId(id);
            return itemRepo.save(updatedItem);
        }
        return null;
    }

    public void deleteItem(Long id) {
        itemRepo.deleteById(id);
    }
}