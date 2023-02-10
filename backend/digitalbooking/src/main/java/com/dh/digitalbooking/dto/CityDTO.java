package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.CityEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityDTO {

    private Integer id;

    @NotEmpty(message = "Name should not be empty.")
    private String name;

    @NotEmpty(message = "UF should not be empty.")
    @Size(min = 2, message = "UF should have at least 2 characters.")
    private String uf;

    @NotEmpty(message = "Description should not be empty.")
    @Size(min = 2, message = "City should have at least 2 characters.")
    private String country;

    public CityDTO(CityEntity city) {
        this.id = city.getId();
        this.name = city.getName();
        this.uf = city.getUf();
        this.country = city.getCountry();
    }
}
