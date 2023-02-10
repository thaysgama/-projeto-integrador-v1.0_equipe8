package com.dh.digitalbooking.dto;


import com.dh.digitalbooking.entities.FavoriteProductEntity;
import com.dh.digitalbooking.entities.RatingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteProductDTO {

    @NotNull(message = "Product should not be null.")
    private Integer productId;

    @NotNull(message = "User should not be null.")
    private Integer userId;

    public FavoriteProductDTO(FavoriteProductEntity favoriteProductEntity) {
        this.productId = favoriteProductEntity.getId().getProduct().getId();
        this.userId = favoriteProductEntity.getId().getUser().getId();
    }
}
