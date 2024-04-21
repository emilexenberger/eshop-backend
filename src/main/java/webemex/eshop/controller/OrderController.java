package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.request.CartItemRequestDTO;
import webemex.eshop.dto.request.OrderItemRequestDTO;
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
import java.util.ArrayList;
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

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<OrderResponseDTO> showOrders() {
        AppUser appUser = usersManagementService.getAuthenticatedUser();
        List<Order> userOrders = orderService.findUserOrders(appUser);

        List<OrderResponseDTO> userOrdersDTO = userOrders.stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
        return userOrdersDTO;
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

        double totalPrice = 0;

//        Move items from cart to order
        List<CartItem> userCartItems = cartItemService.findUserCartItems(appUser);

        for (CartItem userCartItem : userCartItems) {
//            Create a new orderItem
            OrderItem orderItem = new OrderItem(userCartItem);
            orderItem.setOrder(order);
            orderItemService.saveOrderItem(orderItem);

//            Sum up total order price
            totalPrice += userCartItem.getItem().getPrice() * userCartItem.getVolume();

//            Delete the item from cart
            cartItemService.deleteItemById(userCartItem.getId());
        }

//        Save order
        order.setTotalPrice(totalPrice);
        orderService.saveOrder(order);
    }

    @PostMapping("/details")
    @PreAuthorize("isAuthenticated()")
    public List<OrderItemResponseDTO> openOrder(@RequestBody OrderItemRequestDTO req) {
        AppUser appUser = usersManagementService.getAuthenticatedUser();
        UUID orderId = UUID.fromString(req.getOrderId());

        List<OrderItem> userOrderItems = orderItemService.findUserOrderItemsByOrderId(appUser, orderId);
        System.out.println(userOrderItems.size());

        List<OrderItemResponseDTO> userOrderItemsDTO = userOrderItems.stream()
                .map(OrderItemResponseDTO::new)
                .collect(Collectors.toList());

        return userOrderItemsDTO;
    }
}
