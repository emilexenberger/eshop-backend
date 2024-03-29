package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * CartController is responsible for handling cart-related operations.
 * This includes viewing the cart, adding items to the cart, and editing existing cart items.
 * All endpoints are accessible only to authenticated users.
 */
@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UsersManagementService usersManagementService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ItemService itemService;

    /**
     * Retrieve the cart items for the authenticated user.
     *
     * @return A list of CartItemResponseDTO objects representing the items in the user's cart.
     */
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<CartItemResponseDTO> showCart() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return cartItemService.findUserCartItems(appUser).stream()
                .map(CartItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Add an item to the authenticated user's cart.
     *
     * @param req A CartItemRequestDTO containing the item ID and the volume to be added.
     */
    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public void addToCart(@RequestBody CartItemRequestDTO req) {
        UUID itemId = UUID.fromString(req.getItemId());
        int enteredVolume = req.getVolume();

        CartItem cartItem = new CartItem();

        AppUser appUser = usersManagementService.getAuthenticatedUser();
        cartItem.setAppUser(appUser);

        // Adjust the inventory by subtracting the specified volume from the item's stock.
        Item item = itemService.findItemById(itemId);
        item.setVolume(item.getVolume() - enteredVolume);
        itemService.saveItem(item);
        cartItem.setItem(item);

        // Check if the item is already in the cart; if so, update the volume; otherwise, create a new entry.
        Optional<CartItem> matchingItem = cartItemService.findUserCartItems(appUser).stream()
                .filter(existingCartItem -> existingCartItem.getItem().getId().equals(item.getId()))
                .findFirst();

        if (matchingItem.isPresent()) {
            CartItem existingCartItem = matchingItem.get();
            existingCartItem.setVolume(existingCartItem.getVolume() + enteredVolume);
            cartItemService.saveItem(existingCartItem);
        } else {
            cartItem.setVolume(enteredVolume);
            cartItemService.saveItem(cartItem);
        }
    }

    /**
     * Edit an existing cart item in the authenticated user's cart.
     *
     * @param req A CartItemRequestDTO containing the item ID and the new volume.
     */
    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public void editCartItem(@RequestBody CartItemRequestDTO req) {
        UUID itemId = UUID.fromString(req.getItemId());
        int enteredVolume = req.getVolume();
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        // Retrieve the existing cart item and adjust its volume.
        CartItem userCartItem = cartItemService.findUserCartItemById(appUser, itemId);
        int volumeDifference = userCartItem.getVolume() - enteredVolume;
        if (volumeDifference == 0) { return; }

        if (enteredVolume == 0) {
            cartItemService.deleteItemById(userCartItem.getId());
        } else {
            userCartItem.setVolume(enteredVolume);
            cartItemService.saveItem(userCartItem);
        }

        // Update the corresponding item's inventory.
        itemService.findAllItems().stream()
                .filter(item -> item.equals(userCartItem.getItem()))
                .forEach(item -> {
                    item.setVolume(item.getVolume() + volumeDifference);
                    itemService.saveItem(item);
                });
    }
}
