package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Item;
import webemex.eshop.repository.CartItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    public void saveItem(CartItem cartItem) {
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> findAllCartItems() {
        return cartItemRepository.findAll();
    }

    public CartItem findItemById(UUID id) {
        return cartItemRepository.findById(id).get();
    }

    public void deleteItemById(UUID id) {
        cartItemRepository.deleteById(id);
    }

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

    public CartItem findUserCartItemById(AppUser appUser, UUID itemId) {
        List<CartItem> userCartItems = findUserCartItems(appUser);

        CartItem userCartItemById = null;
        for (CartItem userCartItem : userCartItems) {
            if (userCartItem.getItem().getId().equals(itemId)) {
                userCartItemById = userCartItem;
            }
        }

        return userCartItemById;
    }
}
