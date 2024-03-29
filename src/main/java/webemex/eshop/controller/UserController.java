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
import webemex.eshop.service.UsersManagementService;

/**
 * Controller responsible for user-related operations in the e-shop.
 * This includes user registration, login, token refresh, and profile information.
 */
@RestController
@CrossOrigin
public class UserController {

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
