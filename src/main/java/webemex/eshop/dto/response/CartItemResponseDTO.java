package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.CartItem;

@Data
public class CartItemResponseDTO {
    private String id;
    private AppUserResponseDTO appUser;
    private ItemResponseDTO item;
    private int volume;

    public CartItemResponseDTO(CartItem cartItem) {
        this.id = cartItem.getId().toString();
        this.appUser = new AppUserResponseDTO(cartItem.getAppUser());
        this.item = new ItemResponseDTO(cartItem.getItem());
        this.volume = cartItem.getVolume();
    }
}
