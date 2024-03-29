package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webemex.eshop.dto.AuthorizationDTO;
import webemex.eshop.model.AppUser;
import webemex.eshop.repository.UsersRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class responsible for managing user-related operations,
 * such as registration, login, authentication, token management, and user information.
 */
@Service
public class UsersManagementService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user and encodes the password.
     *
     * @param registrationRequest The request data for user registration.
     * @return An `AuthorizationDTO` containing user information and status messages.
     */
    public AuthorizationDTO register(AuthorizationDTO registrationRequest) {
        AuthorizationDTO resp = new AuthorizationDTO();
        try {
            AppUser appUser = new AppUser();
            appUser.setUsername(registrationRequest.getUsername());
            appUser.setSurname(registrationRequest.getSurname());
            appUser.setName(registrationRequest.getName());
            appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            appUser.setRole("USER");
            AppUser savedUser = usersRepository.save(appUser);
            if (savedUser.getId() != null) {
                resp.setAppUser(savedUser);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    /**
     * Authenticates a user and generates JWT tokens.
     *
     * @param loginRequest The login request data with username and password.
     * @return An `AuthorizationDTO` with JWT tokens and status messages.
     */
    public AuthorizationDTO login(AuthorizationDTO loginRequest) {
        AuthorizationDTO response = new AuthorizationDTO();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            AppUser user = usersRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Refreshes a JWT token.
     *
     * @param refreshTokenRequest The request containing the refresh token.
     * @return An `AuthorizationDTO` with the new JWT and status messages.
     */
    public AuthorizationDTO refreshToken(AuthorizationDTO refreshTokenRequest) {
        AuthorizationDTO response = new AuthorizationDTO();
        try {
            String username = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            AppUser user = usersRepository.findByUsername(username).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), user)) {
                String newJwt = jwtUtils.generateToken(user);
                response.setStatusCode(200);
                response.setToken(newJwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Successfully Refreshed Token");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * Retrieves all users from the repository.
     *
     * @return An `AuthorizationDTO` with a list of users and status messages.
     */
    public AuthorizationDTO getAllUsers() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            List<AppUser> allUsers = usersRepository.findAll();
            if (!allUsers.isEmpty()) {
                authorizationDTO.setAppUserList(allUsers);
                authorizationDTO.setStatusCode(200);
                authorizationDTO.setMessage("Successful");
            } else {
                authorizationDTO.setStatusCode(404);
                authorizationDTO.setMessage("No users found");
            }
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred: " + e.getMessage());
        }
        return authorizationDTO;
    }

    /**
     * Retrieves user information by a unique UUID.
     *
     * @param userId The UUID of the user.
     * @return An `AuthorizationDTO` with user details or status messages.
     */
    public AuthorizationDTO getUsersById(UUID userId) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            AppUser user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            authorizationDTO.setAppUser(user);
            authorizationDTO.setStatusCode(200);
            authorizationDTO.setMessage("User with ID '" + userId + "' found successfully");
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred: " + e.getMessage());
        }
        return authorizationDTO;
    }

    /**
     * Deletes a user by their UUID.
     *
     * @param userId The UUID of the user to delete.
     * @return An `AuthorizationDTO` with status and messages about the deletion.
     */
    public AuthorizationDTO deleteUser(UUID userId) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            Optional<AppUser> user = usersRepository.findById(userId);
            if (user.isPresent()) {
                usersRepository.deleteById(userId);
                authorizationDTO.setStatusCode(200);
                authorizationDTO.setMessage("User deleted successfully");
            } else {
                authorizationDTO.setStatusCode(404);
                authorizationDTO.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return authorizationDTO;
    }

    /**
     * Updates a user with new information.
     *
     * @param userId The UUID of the user to update.
     * @param updatedUser The updated user information.
     * @return An `AuthorizationDTO` indicating the success or failure of the update.
     */
    public AuthorizationDTO updateUser(UUID userId, AppUser updatedUser) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            Optional<AppUser> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent()) {
                AppUser existingUser = userOptional.get();
                if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) existingUser.setUsername(updatedUser.getUsername());
                if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) existingUser.setName(updatedUser.getName());
                if (updatedUser.getSurname() != null && !updatedUser.getSurname().isEmpty()) existingUser.setSurname(updatedUser.getSurname());
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                AppUser savedUser = usersRepository.save(existingUser);
                authorizationDTO.setAppUser(savedUser);
                authorizationDTO.setStatusCode(200);
                authorizationDTO.setMessage("User updated successfully");
            } else {
                authorizationDTO.setStatusCode(404);
                authorizationDTO.setMessage("User not found for update");
            }
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return authorizationDTO;
    }

    /**
     * Retrieves the information of the currently authenticated user.
     *
     * @param username The username of the authenticated user.
     * @return An `AuthorizationDTO` with user information.
     */
    public AuthorizationDTO getMyInfo(String username) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            Optional<AppUser> user = usersRepository.findByUsername(username);
            if (user.isPresent()) {
                authorizationDTO.setAppUser(user.get());
                authorizationDTO.setStatusCode(200);
                authorizationDTO.setMessage("Successful");
            } else {
                authorizationDTO.setStatusCode(404);
                authorizationDTO.setMessage("User not found for update");
            }
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return authorizationDTO;
    }

    /**
     * Retrieves the authenticated user from the security context.
     *
     * @return An `AppUser` object with the authenticated user's information.
     */
    public AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthorizationDTO response = getMyInfo(username);

        if (response.getAppUser() == null) {
            throw new RuntimeException("AppUser not found in response.");
        }

        AppUser appUser = new AppUser();
        appUser.setId(UUID.fromString(String.valueOf(response.getAppUser().getId())));
        appUser.setUsername(response.getAppUser().getUsername());
        appUser.setName(response.getAppUser().getName());
        appUser.setSurname(response.getAppUser().getSurname());
        appUser.setPassword(response.getAppUser().getPassword());
        appUser.setRole(response.getAppUser().getRole());
        return appUser;
    }
}
