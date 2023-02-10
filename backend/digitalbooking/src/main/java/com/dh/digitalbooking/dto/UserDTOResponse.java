package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTOResponse {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private RoleDTO role;

    private Set<FavoriteProductDTO> favoriteProducts;


    public UserDTOResponse(UserEntity user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = new RoleDTO(user.getRole());
        this.favoriteProducts = user.getFavoriteProducts() == null ? null : user.getFavoriteProducts().stream().map(FavoriteProductDTO::new).collect(Collectors.toSet());
    }
}
