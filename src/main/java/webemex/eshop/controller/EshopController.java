package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import webemex.eshop.dto.response.ItemResponseDTO;
import webemex.eshop.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for handling e-shop-related requests.
 * This controller provides endpoints for interacting with items in the e-shop.
 *
 * It includes functionality to fetch all items available in the e-shop.
 */
@RestController
@CrossOrigin
@RequestMapping("/eshop")
public class EshopController {

    @Autowired
    private ItemService itemService;

    /**
     * Retrieves all items available in the e-shop.
     *
     * @return a list of {@link ItemResponseDTO} representing all items.
     */
    @GetMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public List<ItemResponseDTO> getAllItems() {
        return itemService.findAllItems().stream()
                .map(ItemResponseDTO::new)
                .collect(Collectors.toList());
    }
}
