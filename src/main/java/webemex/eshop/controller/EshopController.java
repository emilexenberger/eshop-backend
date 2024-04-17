package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.model.Item;
import webemex.eshop.service.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eshop")
public class EshopController {
    @Autowired
    ItemService itemService;

    @GetMapping("/items")
    @PreAuthorize("isAuthenticated()")
    public List<Item> getAllItems() {
        return itemService.findAllItems();
    }
}
