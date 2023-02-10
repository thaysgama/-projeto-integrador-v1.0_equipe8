package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "usuario_tipo")
@Table(name = "usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String firstName;

    @Column(name = "sobrenome")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String password;

    @Column(name = "ativado")
    private Boolean enabled = false;

    @OneToOne
    @JoinColumn(name = "id_funcao")
    private RoleEntity role;

    @OneToMany(mappedBy = "id.user", fetch = FetchType.LAZY)
    private Set<FavoriteProductEntity> favoriteProducts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade ={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE})
    private Set<ConfirmationToken> confirmationToken;

    public UserEntity(String firstName, String lastName, String email, String password, RoleEntity role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
