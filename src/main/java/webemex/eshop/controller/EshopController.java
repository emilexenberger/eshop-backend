package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.response.ItemResponseDTO;
import webemex.eshop.service.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/eshop")
public class EshopController {
    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public List<ItemResponseDTO> getAllItems() {
        return itemService.findAllItems().stream()
                .map(ItemResponseDTO::new)
                .collect(Collectors.toList());
    }
}
