package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.*;

import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.userBuilder;

public class RatingUtils {

    public static RatingEntity ratingBuilder(){
        ProductEntity product = productBuilder();
        UserEntity user = userBuilder();
        RatingPK ratingPK = new RatingPK();
        ratingPK.setProduct(product);
        ratingPK.setUser(user);
        RatingEntity ratingEntity = new RatingEntity(ratingPK,5);
        return ratingEntity;
    }
}
