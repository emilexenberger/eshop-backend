package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.repository.CartItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing cart items in the e-shop application.
 * This class provides methods for creating, retrieving, updating, and deleting cart items.
 */
@Service
public class CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    /**
     * Saves a given cart item to the repository.
     *
     * @param cartItem the cart item to be saved.
     */
    public void saveItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    /**
     * Retrieves all cart items from the repository.
     *
     * @return a list of all cart items.
     */
    public List<CartItem> findAllCartItems() {
        return cartItemRepository.findAll();
    }

    /**
     * Finds a specific cart item by its unique identifier.
     *
     * @param id the UUID of the cart item.
     * @return the cart item with the specified ID.
     */
    public CartItem findItemById(UUID id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
    }

    /**
     * Deletes a cart item by its unique identifier.
     *
     * @param id the UUID of the cart item to delete.
     */
    public void deleteItemById(UUID id) {
        cartItemRepository.deleteById(id);
    }

    /**
     * Finds all cart items belonging to a specific user.
     *
     * @param appUser the user whose cart items are to be retrieved.
     * @return a list of cart items associated with the given user.
     */
    public List<CartItem> findUserCartItems(AppUser appUser) {
        List<CartItem> allCartItems = findAllCartItems();
        List<CartItem> userCartItems = new ArrayList<>();

        for (CartItem existingCartItem : allCartItems) {
            if (existingCartItem.getAppUser().equals(appUser)) {
                userCartItems.add(existingCartItem);
            }
        }
        return userCartItems;
    }

    /**
     * Finds a specific cart item by its ID for a given user.
     *
     * @param appUser the user to whom the cart item belongs.
     * @param itemId the UUID of the item.
     * @return the cart item associated with the user and item ID.
     */
    public CartItem findUserCartItemById(AppUser appUser, UUID itemId) {
        return findUserCartItems(appUser).stream()
                .filter(cartItem -> cartItem.getItem().getId().equals(itemId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Deletes all cart items associated with a specific user.
     *
     * @param appUser the AppUser whose cart items are to be deleted.
     */
    public void deleteUserCartItems(AppUser appUser) {
        if (appUser == null) {
            throw new IllegalArgumentException("AppUser cannot be null");
        }

        List<CartItem> userCartItems = findUserCartItems(appUser);

        for (CartItem cartItem : userCartItems) {
            deleteItemById(cartItem.getId());
        }
    }
}
