package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.AuthorizationDTO;
import webemex.eshop.model.AppUser;
import webemex.eshop.service.UsersManagementService;

import java.util.UUID;

@RestController
@CrossOrigin
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthorizationDTO> register(@RequestBody AuthorizationDTO reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthorizationDTO> login(@RequestBody AuthorizationDTO req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<AuthorizationDTO> refreshToken(@RequestBody AuthorizationDTO req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    @GetMapping("/admin/get-users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> getUSerByID(@PathVariable UUID userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> updateUser(@PathVariable UUID userId, @RequestBody AppUser reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/user/get-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthorizationDTO> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        AuthorizationDTO response = usersManagementService.getMyInfo(username);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorizationDTO> deleteUSer(@PathVariable UUID userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}