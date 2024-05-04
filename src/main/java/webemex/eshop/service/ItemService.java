package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.Item;
import webemex.eshop.repository.ItemRepository;

import java.util.List;
import java.util.UUID;

/**
 * Service class for managing items in the e-shop application.
 * This class provides methods for creating, retrieving, updating, and deleting items in the e-shop.
 */
@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    /**
     * Saves a given item to the repository.
     *
     * @param item the item to be saved.
     */
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * Retrieves all items from the repository.
     *
     * @return a list of all items.
     */
    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    /**
     * Finds a specific item by its unique identifier.
     *
     * @param id the UUID of the item.
     * @return the item with the specified ID.
     */
    public Item findItemById(UUID id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found with ID: " + id));
    }

    /**
     * Deletes an item by its unique identifier.
     *
     * @param id the UUID of the item to delete.
     */
    public void deleteItemById(UUID id) {
        itemRepository.deleteById(id);
    }
}
