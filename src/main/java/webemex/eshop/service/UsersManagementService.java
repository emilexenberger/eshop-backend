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


    public AuthorizationDTO register(AuthorizationDTO registrationRequest){
        AuthorizationDTO resp = new AuthorizationDTO();

        try {
            AppUser appUser = new AppUser();
            appUser.setUsername(registrationRequest.getUsername());
            appUser.setSurname(registrationRequest.getSurname());
            appUser.setName(registrationRequest.getName());
            appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            appUser.setRole("USER");
            AppUser appUserResult = usersRepository.save(appUser);
            if (appUserResult.getId()!=null) {
                resp.setAppUser((appUserResult));
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }


    public AuthorizationDTO login(AuthorizationDTO loginRequest){
        AuthorizationDTO response = new AuthorizationDTO();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));
            var user = usersRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    public AuthorizationDTO refreshToken(AuthorizationDTO refreshTokenReqiest){
        AuthorizationDTO response = new AuthorizationDTO();
        try{
            String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
            AppUser users = usersRepository.findByUsername(ourEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenReqiest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }


    public AuthorizationDTO getAllUsers() {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();

        try {
            List<AppUser> result = usersRepository.findAll();
            if (!result.isEmpty()) {
                authorizationDTO.setAppUserList(result);
                authorizationDTO.setStatusCode(200);
                authorizationDTO.setMessage("Successful");
            } else {
                authorizationDTO.setStatusCode(404);
                authorizationDTO.setMessage("No users found");
            }
            return authorizationDTO;
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred: " + e.getMessage());
            return authorizationDTO;
        }
    }


    public AuthorizationDTO getUsersById(UUID id) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            AppUser usersById = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            authorizationDTO.setAppUser(usersById);
            authorizationDTO.setStatusCode(200);
            authorizationDTO.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred: " + e.getMessage());
        }
        return authorizationDTO;
    }


    public AuthorizationDTO deleteUser(UUID userId) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            Optional<AppUser> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent()) {
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

    public AuthorizationDTO updateUser(UUID userId, AppUser updatedUser) {
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            Optional<AppUser> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent()) {
                AppUser existingUser = userOptional.get();
                if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()) existingUser.setUsername(updatedUser.getUsername());
                if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) existingUser.setName(updatedUser.getName());
                if (updatedUser.getSurname() != null && !updatedUser.getSurname().isEmpty()) existingUser.setSurname(updatedUser.getSurname());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
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


    public AuthorizationDTO getMyInfo(String username){
        AuthorizationDTO authorizationDTO = new AuthorizationDTO();
        try {
            Optional<AppUser> userOptional = usersRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                authorizationDTO.setAppUser(userOptional.get());
                authorizationDTO.setStatusCode(200);
                authorizationDTO.setMessage("successful");
            } else {
                authorizationDTO.setStatusCode(404);
                authorizationDTO.setMessage("User not found for update");
            }

        }catch (Exception e){
            authorizationDTO.setStatusCode(500);
            authorizationDTO.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return authorizationDTO;

    }

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
