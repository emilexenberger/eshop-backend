package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.OrderItem;

@Data
public class OrderItemResponseDTO {
    private String id;
    private AppUserResponseDTO appUser;
    private ItemResponseDTO item;
    private OrderResponseDTO order;
    private int volume;

    public OrderItemResponseDTO(OrderItem orderItem) {
        this.id = orderItem.getId().toString();
        this.appUser = new AppUserResponseDTO(orderItem.getAppUser());
        this.item = new ItemResponseDTO(orderItem.getItem());
        this.order = new OrderResponseDTO(orderItem.getOrder());
        this.volume = orderItem.getVolume();
    }
}
