//package webemex.eshop.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import webemex.eshop.model.Item;
//import webemex.eshop.service.ItemService;
//
//import java.util.List;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/eshop")
//public class CartController {
//    @Autowired
//    ItemService itemService;
//
//    @GetMapping("/items")
//    @PreAuthorize("isAuthenticated()")
//    public List<Item> getAllItems() {
//        return itemService.findAllItems();
//    }
//}