package com.dh.digitalbooking.dto;

import com.dh.digitalbooking.entities.CharacteristicEntity;
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
public class CharacteristicDTO {

    private Integer id;

    @NotEmpty(message = "Name should not be empty.")
    @Size(min = 2, message = "name should have at least 2 characters")
    private String name;

    @NotEmpty(message = "Icon should not be empty.")
    private String icon;

    public CharacteristicDTO(CharacteristicEntity characteristic) {
        this.id = characteristic.getId();
        this.name = characteristic.getName();
        this.icon = characteristic.getIcon();
    }
}
