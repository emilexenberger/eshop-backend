package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem findOrderItemById(UUID id) {
        return orderItemRepository.findById(id).get();
    }

    public void deleteOrderItemById(UUID id) {
        orderItemRepository.deleteById(id);
    }

    public List<OrderItem> findUserOrderItems(AppUser appUser) {
        return findAllOrderItems().stream()
                .filter(orderItem -> orderItem.getAppUser().equals(appUser))
                .collect(Collectors.toList());
    }

    public OrderItem findUserOrderItemById(AppUser appUser, UUID orderId) {
        return findUserOrderItems(appUser).stream()
                .filter(orderItem -> orderItem.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    public List<OrderItem> findUserOrderItemsByOrderId(AppUser appUser, UUID orderId) {
        return findUserOrderItems(appUser).stream()
                .filter(orderItem -> orderItem.getOrder().getId().equals(orderId))
                .collect(Collectors.toList());
    }
}
