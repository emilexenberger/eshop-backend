package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Item;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CartItemResponseDTO> listCartItemResponseDTO(List<CartItem> listCartItem) {
        List<CartItemResponseDTO> listCartItemResponseDTO = listCartItem.stream()
                .map(CartItemResponseDTO::new)
                .collect(Collectors.toList());
        return listCartItemResponseDTO;
    }
}
