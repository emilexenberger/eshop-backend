package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.Order;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
    private String id;
    private LocalDateTime dateTime;
    double totalPrice;
    private AppUserResponseDTO appUser;

    public OrderResponseDTO(Order order) {
        this.id = order.getId().toString();
        this.dateTime = order.getDateTime();
        this.totalPrice = order.getTotalPrice();
        this.appUser = new AppUserResponseDTO(order.getAppUser());
    }
}