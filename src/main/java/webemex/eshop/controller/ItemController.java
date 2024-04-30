package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.request.ItemRequestDTO;
import webemex.eshop.dto.response.ItemResponseDTO;
import webemex.eshop.model.Item;
import webemex.eshop.service.ItemService;

import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void saveItem(@RequestBody ItemRequestDTO req) {
        Item item = new Item();

        if (req.getId() != null) item.setId(UUID.fromString(req.getId()));
        if (req.getProductName() != null) item.setProductName(req.getProductName());
        if (req.getProductCode() != null) item.setProductCode(req.getProductCode());
        if (req.getPrice() != null) item.setPrice(req.getPrice());
        if (req.getVolume() != null) item.setVolume(req.getVolume());

        itemService.saveItem(item);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeItem(@RequestBody ItemRequestDTO req) {
        UUID itemId = UUID.fromString(req.getId());
        itemService.deleteItemById(itemId);
    }

    @GetMapping("/details/{itemId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ItemResponseDTO getItemById(@PathVariable UUID itemId){
        ItemResponseDTO response = new ItemResponseDTO(itemService.findItemById(itemId));
        return response;

    }
}
