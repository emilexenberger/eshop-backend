package webemex.eshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import webemex.eshop.dto.ReqRes;
import webemex.eshop.model.AppUser;
import webemex.eshop.service.UsersManagementService;

import java.util.UUID;

@RestController
@CrossOrigin
public class UserManagementController {
    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/auth/register")
    public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ReqRes> login(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    @GetMapping("/admin/get-all-users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReqRes> getAllUsers(){
        return ResponseEntity.ok(usersManagementService.getAllUsers());

    }

    @GetMapping("/admin/get-users/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReqRes> getUSerByID(@PathVariable UUID userId){
        return ResponseEntity.ok(usersManagementService.getUsersById(userId));

    }

    @PutMapping("/admin/update/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReqRes> updateUser(@PathVariable UUID userId, @RequestBody AppUser reqres){
        return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
    }

    @GetMapping("/user/get-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReqRes> getMyProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        ReqRes response = usersManagementService.getMyInfo(username);
        return  ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/admin/delete/{userId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ReqRes> deleteUSer(@PathVariable UUID userId){
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}