package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.dto.RoleDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "funcoes")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;

    public RoleEntity(RoleDTO roleDTO) {
        this.id = roleDTO.getId();
        this.name = roleDTO.getName();
    }

    public RoleEntity(String name) {
        this.name = name;
    }
}
