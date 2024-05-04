package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

/**
 * Controller for managing orders in the e-shop.
 * This controller provides endpoints for creating new orders, viewing existing orders,
 * and retrieving order details.
 */
@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UsersManagementService usersManagementService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * Retrieves all orders for the authenticated user.
     *
     * @return a list of {@link OrderResponseDTO} representing all orders of the current user.
     */
    @GetMapping("/show-all")
    @PreAuthorize("isAuthenticated()")
    public List<OrderResponseDTO> showOrders() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return orderService.findUserOrders(appUser).stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Places a new order from the current user's cart items.
     * This endpoint creates a new order and moves the cart items to the order.
     */
    @GetMapping("/place")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(value = HttpStatus.OK)
    public void placeOrder() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();
        LocalDateTime dateTime = LocalDateTime.now();

        Order order = new Order();
        order.setDateTime(dateTime);
        order.setAppUser(appUser);
        orderService.saveOrder(order);

        List<CartItem> userCartItems = cartItemService.findUserCartItems(appUser);

        double totalPrice = userCartItems.stream()
                .mapToDouble(cartItem -> cartItem.getItem().getPrice() * cartItem.getVolume())
                .sum();

        userCartItems.forEach(cartItem -> {
            OrderItem orderItem = new OrderItem(cartItem);
            orderItem.setOrder(order);
            orderItemService.saveOrderItem(orderItem);
            cartItemService.deleteItemById(cartItem.getId());
        });

        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);
    }

    /**
     * Retrieves all order items for a specific order.
     *
     * @param orderId the UUID of the order.
     * @return a list of {@link OrderItemResponseDTO} for the specified order.
     */
    @GetMapping("/items/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public List<OrderItemResponseDTO> openOrder(@PathVariable UUID orderId) {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return orderItemService.findUserOrderItemsByOrderId(appUser, orderId).stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the details of a specific order.
     *
     * @param orderId the UUID of the order.
     * @return an {@link OrderResponseDTO} containing the details of the order.
     */
    @GetMapping("/details/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public OrderResponseDTO getOrderById(@PathVariable UUID orderId) {
        AppUser appUser = usersManagementService.getAuthenticatedUser();

        return new OrderResponseDTO(orderService.findUserOrderById(appUser, orderId));
    }
}
