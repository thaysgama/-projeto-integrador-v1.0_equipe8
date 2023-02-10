package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.dto.CharacteristicDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "caracteristicas")
public class CharacteristicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "icone")
    private String icon;

    @ManyToMany(mappedBy = "characteristics", fetch = FetchType.LAZY, cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    private Set<ProductEntity> products;

    public CharacteristicEntity(CharacteristicDTO characteristicDTO) {
        this.id = characteristicDTO.getId();
        this.name = characteristicDTO.getName();
        this.icon = characteristicDTO.getIcon();
    }

    public CharacteristicEntity(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
}
