package com.dh.digitalbooking.dto;


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
public class RatingDTO {

    @Min(value = 1, message = "Score should not be less than 1.")
    @Max(value = 5, message = "Score should not be more than 5.")
    private Integer score;

    @NotNull(message = "Product should not be null.")
    private Integer productId;

    @NotNull(message = "User should not be null.")
    private Integer userId;

    public RatingDTO(RatingEntity ratingEntity) {
        this.score = ratingEntity.getScore();
        this.productId = ratingEntity.getId().getProduct().getId();
        this.userId = ratingEntity.getId().getUser().getId();
    }
}
