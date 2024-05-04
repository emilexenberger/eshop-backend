package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.Order;
import webemex.eshop.repository.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling operations related to Order management.
 * Provides functionality for saving, retrieving, updating, and deleting orders,
 * as well as fetching orders based on various criteria like user or order ID.
 */
@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    /**
     * Saves an Order object to the repository.
     *
     * @param order The Order object to save.
     */
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    /**
     * Retrieves all Order objects from the repository.
     *
     * @return A list of all orders.
     */
    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Finds an Order by its unique ID.
     *
     * @param id The UUID of the order.
     * @return The Order object with the specified ID, or null if not found.
     */
    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    /**
     * Deletes an Order by its unique ID.
     *
     * @param id The UUID of the order to delete.
     */
    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }

    /**
     * Finds all orders associated with a specific user.
     *
     * @param appUser The AppUser whose orders are to be retrieved.
     * @return A list of Order objects belonging to the specified user.
     */
    public List<Order> findUserOrders(AppUser appUser) {
        return findAllOrders().stream()
                .filter(order -> order.getAppUser().equals(appUser))
                .collect(Collectors.toList());
    }

    /**
     * Finds an Order by its ID for a specific user.
     *
     * @param appUser The AppUser whose order is to be retrieved.
     * @param orderId The UUID of the order.
     * @return The Order object with the specified ID for the given user, or null if not found.
     */
    public Order findUserOrderById(AppUser appUser, UUID orderId) {
        return findUserOrders(appUser).stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Deletes all orders associated with a specific user.
     *
     * @param appUser The AppUser whose orders are to be deleted.
     */
    public void deleteUserOrders(AppUser appUser) {
        if (appUser == null) {
            throw new IllegalArgumentException("AppUser cannot be null");
        }

        List<Order> userOrders = findUserOrders(appUser);

        for (Order order : userOrders) {
            deleteOrderById(order.getId());
        }
    }
}
