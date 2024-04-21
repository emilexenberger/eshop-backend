package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;
import webemex.eshop.repository.OrderItemRepository;
import webemex.eshop.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).get();
    }

    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }

    public List<Order> findUserOrders(AppUser appUser) {
        List<Order> allOrders = findAllOrders();
        List<Order> userOrders = new ArrayList<>();
        for (Order existingOrder : allOrders) {
            if (existingOrder.getAppUser().equals(appUser)) {
                userOrders.add(existingOrder);
            }
        }
        return userOrders;
    }

    public Order findUserOrderById(AppUser appUser, UUID orderId) {
        List<Order> userOrders = findUserOrders(appUser);

//        TODO: Nastuduj Stream a prepis tento kod do streamu a vsetky kody zo Service, kde robim podobne veci cez FOR loop
        Order userOrderById = null;
        for (Order userOrder : userOrders) {
            if (userOrder.getId().equals(orderId)) {
                userOrderById = userOrder;
            }
        }

        return userOrderById;
    }
}
