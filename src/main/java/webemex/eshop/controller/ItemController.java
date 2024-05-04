package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.request.ItemRequestDTO;
import webemex.eshop.dto.response.ItemResponseDTO;
import webemex.eshop.model.Item;
import webemex.eshop.service.ItemService;

import java.util.UUID;

/**
 * Controller for managing items in the e-shop.
 * This controller provides endpoints for adding, deleting, and retrieving item details.
 *
 * Only administrators with the proper authority are allowed to access certain endpoints.
 */
@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Saves an item to the e-shop.
     * This method is used to add new items or update existing ones.
     *
     * @param req the {@link ItemRequestDTO} containing item information.
     */
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveItem(@RequestBody ItemRequestDTO req) {
        Item item = new Item();

        if (req.getId() != null) item.setId(UUID.fromString(req.getId()));
        if (req.getProductName() != null) item.setProductName(req.getProductName());
        if (req.getProductCode() != null) item.setProductCode(req.getProductCode());
        if (req.getPrice() != null) item.setPrice(req.getPrice());
        if (req.getVolume() != null) item.setVolume(req.getVolume());

        itemService.saveItem(item);
    }

    /**
     * Deletes an item from the e-shop.
     *
     * @param req the {@link ItemRequestDTO} with the ID of the item to be deleted.
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public void removeItem(@RequestBody ItemRequestDTO req) {
        UUID itemId = UUID.fromString(req.getId());
        itemService.deleteItemById(itemId);
    }

    /**
     * Retrieves item details by its ID.
     *
     * @param itemId the ID of the item to be retrieved.
     * @return the {@link ItemResponseDTO} containing item details.
     */
    @GetMapping("/details/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDTO getItemById(@PathVariable UUID itemId){
        return new ItemResponseDTO(itemService.findItemById(itemId));
    }
}
