package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.AuthorizationDTO;
import webemex.eshop.dto.request.IsUsernameAvailableRequestDTO;
import webemex.eshop.dto.response.IsUsernameAvailableResponseDTO;
import webemex.eshop.model.AppUser;
import webemex.eshop.service.UsersManagementService;

import java.util.UUID;

/**
 * Controller responsible for managing user-related operations in the e-shop.
 * It handles user registration, login, token refresh, and administrative operations such as updating and deleting users.
 */
@RestController
@CrossOrigin
public class UserManagementController {

    @Autowired
    private UsersManagementService usersManagementService;

    /**
     * Registers a new user in the system.
     *
     * @param reg the {@link AuthorizationDTO} containing registration information.
     * @return a {@link ResponseEntity} with an AuthorizationDTO containing the authentication token for the registered user.
     */
    @PostMapping("/auth/register")
    public ResponseEntity<AuthorizationDTO> register(@RequestBody AuthorizationDTO reg) {
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    /**
     * Authenticates a user and provides an authorization token upon successful login.
     *
     * @param req the {@link AuthorizationDTO} containing login information.
     * @return a {@link ResponseEntity} with an AuthorizationDTO containing the authentication token for the logged-in user.
     */
    @PostMapping("/auth/login")
    public ResponseEntity<AuthorizationDTO> login(@RequestBody AuthorizationDTO req) {
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    /**
     * Refreshes the authentication token for the user.
     *
     * @param req the {@link AuthorizationDTO} containing the refresh token.
     * @return a {@link ResponseEntity} with an updated AuthorizationDTO containing the new token.
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthorizationDTO> refreshToken(@RequestBody AuthorizationDTO req) {
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

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
     * @return a {@link ResponseEntity} with the AuthorizationDTO containing the user's details.
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
     * @param reqres the updated {@link AppUser} information.
     * @return a {@link ResponseEntity} with the updated AuthorizationDTO.
     */
    @PutMapping("/admin/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> updateUser(@PathVariable UUID userId, @RequestBody AppUser reqres) {
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @return a {@link ResponseEntity} with the AuthorizationDTO containing the user's profile information.
     */
    @GetMapping("/user/get-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthorizationDTO> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthorizationDTO response = usersManagementService.getMyInfo(username);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Deletes a specific user by their ID.
     *
     * @param userId the UUID of the user to delete.
     * @return a {@link ResponseEntity} indicating the outcome of the deletion.
     */
    @DeleteMapping("/admin/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> deleteUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }

    /**
     * Checks if a given username is available.
     *
     * @param req the {@link IsUsernameAvailableRequestDTO} containing the username to check.
     * @return an {@link IsUsernameAvailableResponseDTO} indicating whether the username is available.
     */
    @PostMapping("/user/is-username-available")
    public IsUsernameAvailableResponseDTO isUsernameAvailable(@RequestBody IsUsernameAvailableRequestDTO req) {
        AuthorizationDTO allUsersDTO = usersManagementService.getAllUsers();

        boolean isAvailable = allUsersDTO.getAppUserList().stream()
                .noneMatch(user -> user.getUsername().equals(req.getUsername()));

        IsUsernameAvailableResponseDTO response = new IsUsernameAvailableResponseDTO();
        response.setIsUsernameAvailable(isAvailable);
        return response;
    }
}
