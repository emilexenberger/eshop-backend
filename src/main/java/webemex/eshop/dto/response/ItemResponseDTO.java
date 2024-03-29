package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.Item;

@Data
public class ItemResponseDTO {
    private String id;
    private String productName;
    private int productCode;
    private double price;
    private int volume;

    public ItemResponseDTO(Item item) {
        this.id = item.getId().toString();
        this.productName = item.getProductName();
        this.productCode = item.getProductCode();
        this.price = item.getPrice();
        this.volume = item.getVolume();
    }
}
