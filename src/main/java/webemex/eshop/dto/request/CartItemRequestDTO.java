package webemex.eshop.dto.request;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private String itemId;
    private int volume;
}
