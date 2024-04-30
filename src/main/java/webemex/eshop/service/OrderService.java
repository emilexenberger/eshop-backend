package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.Order;
import webemex.eshop.repository.OrderRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return findAllOrders().stream()
                .filter(order -> order.getAppUser().equals(appUser))
                .collect(Collectors.toList());
    }

    public Order findUserOrderById(AppUser appUser, UUID orderId) {
        return findUserOrders(appUser).stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }
}
