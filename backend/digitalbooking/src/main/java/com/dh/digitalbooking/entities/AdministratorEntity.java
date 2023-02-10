package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = UserType.Values.ADMINISTRADOR)
@Entity
public class AdministratorEntity extends UserEntity{

    public AdministratorEntity(String firstName, String lastName, String email, String password, RoleEntity role) {
        super(firstName, lastName, email, password, role);
    }
}
