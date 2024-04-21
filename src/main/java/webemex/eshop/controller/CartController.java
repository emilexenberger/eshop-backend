package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.request.CartItemRequestDTO;
import webemex.eshop.dto.response.CartItemResponseDTO;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Item;
import webemex.eshop.service.CartItemService;
import webemex.eshop.service.ItemService;
import webemex.eshop.service.UsersManagementService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {
    @Autowired
    UsersManagementService usersManagementService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    ItemService itemService;

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<CartItemResponseDTO> showCart() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();
        List<CartItem> userCartItems = cartItemService.findUserCartItems(appUser);

        List<CartItemResponseDTO> userCartItemsDTO = userCartItems.stream()
                .map(CartItemResponseDTO::new)
                .collect(Collectors.toList());
        return userCartItemsDTO;
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public void addToCart(@RequestBody CartItemRequestDTO req) {
        UUID itemId = UUID.fromString(req.getItemId());
        int enteredVolume = req.getVolume();

//        Create empty cartItem
        CartItem cartItem = new CartItem();

//        Assign appUser to cartItem
        AppUser appUser = usersManagementService.getAuthenticatedUser();
        cartItem.setAppUser(appUser);

//        Subtract volume of item from inventory and assign item to cartItem
        Item item = itemService.findItemById(itemId);
        item.setVolume(item.getVolume() - enteredVolume);
        itemService.saveItem(item);
        cartItem.setItem(item);

//        Get list of all userCartItems
        List<CartItem> userCartItems = cartItemService.findUserCartItems(appUser);

//        If the item already is in the cart, just add up to entered volume, otherwise create a new entry for CartItem table in the database
        for (CartItem existingUserCartItem : userCartItems) {
            if (existingUserCartItem.getItem().getId().equals(item.getId())) {
                System.out.println("Existuje zaznam s tymto itemom pre tohto pouzivatela");
                System.out.println("ID zaznamu v cart_item tabulke je: " + existingUserCartItem.getId());
                existingUserCartItem.setVolume(existingUserCartItem.getVolume() + enteredVolume);
                cartItemService.saveItem(existingUserCartItem);
                return;
            }
        }
        System.out.println("Neexistuje zaznam s tymto itemom pre tohto pouzivatela");
        cartItem.setVolume(enteredVolume);
        cartItemService.saveItem(cartItem);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public void editCartItem(@RequestBody CartItemRequestDTO req) {
        UUID itemId = UUID.fromString(req.getItemId());
        int enteredVolume = req.getVolume();
        AppUser appUser = usersManagementService.getAuthenticatedUser();

//        Change volume in the table cart_item
        CartItem userCartItem = cartItemService.findUserCartItemById(appUser, itemId);
        int itemDiff = userCartItem.getVolume() - enteredVolume;
        if (enteredVolume == 0) {
            cartItemService.deleteItemById(userCartItem.getId());
        } else {
            userCartItem.setVolume(enteredVolume);
            cartItemService.saveItem(userCartItem);
        }

//        Consider the change in volumes and in the table item
        List<Item> allItems = itemService.findAllItems();
        for (Item item : allItems) {
            if (item.equals(userCartItem.getItem())) {
                item.setVolume(item.getVolume() + itemDiff);
                itemService.saveItem(item);
            }
        }
    }
}
