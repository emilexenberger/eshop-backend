package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.AppUser;

@Data
public class AppUserResponseDTO {
//    private String id;
    private String username;
    private String name;
    private String surname;
//    private String role;

    public AppUserResponseDTO(AppUser appUser) {
//        this.id = appUser.getId().toString();
        this.username = appUser.getUsername();
        this.name = appUser.getName();
        this.surname = appUser.getSurname();
//        this.role = appUser.getRole();
    }
}
