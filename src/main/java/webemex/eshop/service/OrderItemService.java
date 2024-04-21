package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        List<OrderItem> allOrderItems = findAllOrderItems();
        List<OrderItem> userOrderItems = new ArrayList<>();
        for (OrderItem existingOrderItem : allOrderItems) {
            if (existingOrderItem.getAppUser().equals(appUser)) {
                userOrderItems.add(existingOrderItem);
            }
        }
        return userOrderItems;
    }

    public OrderItem findUserOrderItemById(AppUser appUser, UUID orderId) {
        List<OrderItem> userOrderItems = findUserOrderItems(appUser);

        OrderItem userOrderItemById = null;
        for (OrderItem userOrderItem : userOrderItems) {
            if (userOrderItem.getId().equals(orderId)) {
                userOrderItemById = userOrderItem;
            }
        }

        return userOrderItemById;
    }

    public List<OrderItem> findUserOrderItemsByOrderId(AppUser appUser, UUID orderId) {
        List<OrderItem> userOrderItems = findUserOrderItems(appUser);
        List<OrderItem> userOrderItemsByOrderId = new ArrayList<>();
        for (OrderItem userOrderItem : userOrderItems) {
            if (userOrderItem.getOrder().getId().equals(orderId)) {
                userOrderItemsByOrderId.add(userOrderItem);
            }
        }
        return userOrderItemsByOrderId;
    }
}
