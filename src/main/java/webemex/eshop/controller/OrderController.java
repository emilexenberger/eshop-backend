package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.response.OrderItemResponseDTO;
import webemex.eshop.dto.response.OrderResponseDTO;
import webemex.eshop.model.AppUser;
import webemex.eshop.model.CartItem;
import webemex.eshop.model.Order;
import webemex.eshop.model.OrderItem;
import webemex.eshop.service.CartItemService;
import webemex.eshop.service.OrderItemService;
import webemex.eshop.service.OrderService;
import webemex.eshop.service.UsersManagementService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    @Autowired
    UsersManagementService usersManagementService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    @GetMapping("/show-all")
    @PreAuthorize("isAuthenticated()")
    public List<OrderResponseDTO> showOrders() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return orderService.findUserOrders(appUser).stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/place")
    @PreAuthorize("isAuthenticated()")
    public void placeOrder() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();
        LocalDateTime dateTime = LocalDateTime.now();

//        Create a new order
        Order order = new Order();
        order.setDateTime(dateTime);
        order.setAppUser(appUser);
        orderService.saveOrder(order);

//        Move items from cart to order
        List<CartItem> userCartItems = cartItemService.findUserCartItems(appUser);

//        Total price
        double totalPrice = userCartItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getVolume()) // Spočítať cenu za každý položku
                .sum();

//        Move items from cart to order
        userCartItems.forEach(cartItem -> {
//            Move to order
            OrderItem orderItem = new OrderItem(cartItem);
            orderItem.setOrder(order);
            orderItemService.saveOrderItem(orderItem);

//            Delete from cart
            cartItemService.deleteItemById(cartItem.getId());
        });

//        Save order
        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);
    }

    @GetMapping("/items/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public List<OrderItemResponseDTO> openOrder(@PathVariable UUID orderId) {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return orderItemService.findUserOrderItemsByOrderId(appUser, orderId).stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/details/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public OrderResponseDTO getOrderById(@PathVariable UUID orderId) {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return new OrderResponseDTO(orderService.findUserOrderById(appUser, orderId));
    }
}