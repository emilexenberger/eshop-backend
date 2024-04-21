package webemex.eshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webemex.eshop.dto.ReqRes;
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


    public ReqRes register(ReqRes registrationRequest){
        ReqRes resp = new ReqRes();

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


    public ReqRes login(ReqRes loginRequest){
        ReqRes response = new ReqRes();
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





    public ReqRes refreshToken(ReqRes refreshTokenReqiest){
        ReqRes response = new ReqRes();
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


    public ReqRes getAllUsers() {
        ReqRes reqRes = new ReqRes();

        try {
            List<AppUser> result = usersRepository.findAll();
            if (!result.isEmpty()) {
                reqRes.setAppUserList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }


    public ReqRes getUsersById(UUID id) {
        ReqRes reqRes = new ReqRes();
        try {
            AppUser usersById = usersRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setAppUser(usersById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Users with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes deleteUser(UUID userId) {
        ReqRes reqRes = new ReqRes();
        try {
            Optional<AppUser> userOptional = usersRepository.findById(userId);
            if (userOptional.isPresent()) {
                usersRepository.deleteById(userId);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User deleted successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for deletion");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while deleting user: " + e.getMessage());
        }
        return reqRes;
    }

    public ReqRes updateUser(UUID userId, AppUser updatedUser) {
        ReqRes reqRes = new ReqRes();
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
                reqRes.setAppUser(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }


    public ReqRes getMyInfo(String username){
        ReqRes reqRes = new ReqRes();
        try {
            Optional<AppUser> userOptional = usersRepository.findByUsername(username);
            if (userOptional.isPresent()) {
                reqRes.setAppUser(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }
}
