package com.dh.digitalbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTORequest {

    private Integer id;

    @NotEmpty(message = "Name should not be empty.")
    private String name;

    @NotEmpty(message = "Description should not be empty.")
    private String description;

    @NotEmpty(message = "Address should not be empty.")
    private String address;

    private Double latitude;

    private Double longitude;

    @NotEmpty(message = "General rules should not be empty.")
    private String generalRules;

    @NotEmpty(message = "Safety rules should not be empty.")
    private String safetyRules;

    @NotEmpty(message = "Cancellation rules should not be empty.")
    private String cancellationRules;

    private List<Integer> characteristicIds;

    private List<ImageDTO> imageList;

    @NotNull(message = "Category should not be null.")
    private Integer categoryId;

    @NotNull(message = "City should not be null.")
    private Integer cityId;

    private Integer proprietorId;


    public ProductDTORequest(String name, String description, String address, Double latitude,
                             Double longitude, String generalRules, String safetyRules, String cancellationRules,
                             List<Integer> characteristicIds, List<ImageDTO> imageList, Integer categoryId,
                             Integer cityId, Integer proprietorId) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.generalRules = generalRules;
        this.safetyRules = safetyRules;
        this.cancellationRules = cancellationRules;
        this.characteristicIds = characteristicIds;
        this.imageList = imageList;
        this.categoryId = categoryId;
        this.cityId = cityId;
        this.proprietorId = proprietorId;
    }
}
