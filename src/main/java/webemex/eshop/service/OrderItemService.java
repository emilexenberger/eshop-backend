package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This service class provides methods for managing order items in the e-shop application.
 * It allows for saving, finding, and deleting order items, as well as retrieving order items by specific criteria.
 */
@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    /**
     * Saves an order item to the database.
     *
     * @param orderItem The order item to be saved.
     */
    public void saveOrderItem(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    /**
     * Retrieves all order items from the database.
     *
     * @return A list of all order items.
     */
    public List<OrderItem> findAllOrderItems() {
        return orderItemRepository.findAll();
    }

    /**
     * Finds an order item by its unique ID.
     *
     * @param id The UUID of the order item.
     * @return The order item with the given ID, or null if not found.
     */
    public OrderItem findOrderItemById(UUID id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    /**
     * Deletes an order item by its unique ID.
     *
     * @param id The UUID of the order item to be deleted.
     */
    public void deleteOrderItemById(UUID id) {
        orderItemRepository.deleteById(id);
    }

    /**
     * Finds all order items associated with a specific user.
     *
     * @param appUser The user whose order items are to be found.
     * @return A list of order items for the given user.
     */
    public List<OrderItem> findUserOrderItems(AppUser appUser) {
        return findAllOrderItems().stream()
                .filter(orderItem -> orderItem.getAppUser().equals(appUser))
                .collect(Collectors.toList());
    }

    /**
     * Finds a specific order item for a given user by its unique ID.
     *
     * @param appUser The user whose order item is to be found.
     * @param orderId The UUID of the order item.
     * @return The order item with the given ID for the specified user, or null if not found.
     */
    public OrderItem findUserOrderItemById(AppUser appUser, UUID orderId) {
        return findUserOrderItems(appUser).stream()
                .filter(orderItem -> orderItem.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds all order items for a given user that belong to a specific order.
     *
     * @param appUser The user whose order items are to be found.
     * @param orderId The UUID of the order.
     * @return A list of order items for the specified order and user.
     */
    public List<OrderItem> findUserOrderItemsByOrderId(AppUser appUser, UUID orderId) {
        return findUserOrderItems(appUser).stream()
                .filter(orderItem -> orderItem.getOrder().getId().equals(orderId))
                .collect(Collectors.toList());
    }

    /**
     * Deletes all order items associated with a specific user.
     *
     * @param appUser The user whose order items are to be deleted.
     */
    public void deleteUserOrderItems(AppUser appUser) {
        List<OrderItem> userOrderItems = findUserOrderItems(appUser);

        for (OrderItem orderItem : userOrderItems) {
            deleteOrderItemById(orderItem.getId());
        }
    }

}
