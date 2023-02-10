package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = UserType.Values.CLIENTE)
@Entity
public class ClientEntity extends UserEntity{

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<BookingEntity> bookings;

    public ClientEntity(String firstName, String lastName, String email, String password, RoleEntity role) {
        super(firstName, lastName, email, password, role);
    }
}
