package com.dh.digitalbooking.entities;

import com.dh.digitalbooking.dto.CityDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "cidades")
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    private String name;

    @Column(name = "uf")
    private String uf;

    @Column(name = "pais")
    private String country;

    @OneToMany (mappedBy = "city", fetch = FetchType.LAZY)
    private Set<ProductEntity> products;

    public CityEntity(CityDTO cityDTO) {
        this.id = cityDTO.getId();
        this.name = cityDTO.getName();
        this.uf = cityDTO.getUf();
        this.country = cityDTO.getCountry();
    }

    public CityEntity(String name, String uf, String country) {
        this.name = name;
        this.uf = uf;
        this.country = country;
    }
}
