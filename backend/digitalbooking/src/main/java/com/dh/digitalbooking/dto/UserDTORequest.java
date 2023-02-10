package com.dh.digitalbooking.dto;


import com.dh.digitalbooking.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTORequest {

    private Integer id;

    @NotEmpty(message = "First name should not be empty.")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty.")
    private String lastName;

    @Email(message = "Field must be a valid email.")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    private Integer roleId;


    public UserDTORequest(String firstName, String lastName, String email, String password, Integer roleId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }
}
