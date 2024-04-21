package webemex.eshop.dto.response;

import lombok.Data;
import webemex.eshop.model.AppUser;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<AppUserResponseDTO> ListAppUserResponseDTO(List<AppUser> listAppUser) {
        List<AppUserResponseDTO> listAppUserResponseDTO = listAppUser.stream()
                .map(AppUserResponseDTO::new)
                .collect(Collectors.toList());
        return listAppUserResponseDTO;
    }
}
