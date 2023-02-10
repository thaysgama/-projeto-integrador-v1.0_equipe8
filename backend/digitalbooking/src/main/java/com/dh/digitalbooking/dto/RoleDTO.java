package com.dh.digitalbooking.dto;


import com.dh.digitalbooking.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private Integer id;

    @NotEmpty(message = "Name should not be empty.")
    private String name;

    public RoleDTO(RoleEntity role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
