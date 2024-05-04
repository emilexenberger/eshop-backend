package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.AuthorizationDTO;
import webemex.eshop.model.AppUser;
import webemex.eshop.service.UsersManagementService;
import webemex.eshop.service.CartItemService;
import webemex.eshop.service.OrderItemService;
import webemex.eshop.service.OrderService;

import java.util.UUID;

/**
 * Controller for administrator operations in the e-shop.
 * This includes retrieving all users, updating and deleting users, and other admin-related tasks.
 */
@RestController
@CrossOrigin
public class AdminController {

    @Autowired
    private UsersManagementService usersManagementService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * Retrieves a list of all users, accessible only by administrators.
     *
     * @return a {@link ResponseEntity} with an AuthorizationDTO containing all registered users.
     */
    @GetMapping("/admin/get-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> getAllUsers() {
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    /**
     * Retrieves information about a specific user by their ID.
     *
     * @param userId the UUID of the user to retrieve.
     * @return a {@link ResponseEntity} with an AuthorizationDTO containing the user's details.
     */
    @GetMapping("/admin/user/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> getUserByID(@PathVariable UUID userId) {
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));
    }

    /**
     * Updates information about a specific user.
     *
     * @param userId the UUID of the user to update.
     * @param req the updated {@link AppUser} information.
     * @return a {@link ResponseEntity} with the updated AuthorizationDTO.
     */
    @PutMapping("/admin/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> updateUser(@PathVariable UUID userId, @RequestBody AppUser req) {
        return ResponseEntity.ok(usersManagementService.updateUser(userId, req));
    }

    /**
     * Deletes a specific user by their ID.
     *
     * @param userId the UUID of the user to delete.
     */
    @DeleteMapping("/admin/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable UUID userId) {
        AppUser appUser = usersManagementService.getUsersById(userId).getAppUser();

        cartItemService.deleteUserCartItems(appUser);
        orderItemService.deleteUserOrderItems(appUser);
        orderService.deleteUserOrders(appUser);
        usersManagementService.deleteUser(userId);
    }
}
