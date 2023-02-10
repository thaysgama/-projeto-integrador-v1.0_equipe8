package com.dh.digitalbooking.utils;

import com.dh.digitalbooking.entities.*;

import static com.dh.digitalbooking.utils.ProductUtils.productBuilder;
import static com.dh.digitalbooking.utils.UserUtils.userBuilder;


public class FavoriteProductUtils {


    public static FavoriteProductEntity favoriteProductBuilder(){
        ProductEntity product = productBuilder();
        UserEntity user = userBuilder();
        FavoriteProductPK favoriteProductPK = new FavoriteProductPK();
        favoriteProductPK.setProduct(product);
        favoriteProductPK.setUser(user);
        FavoriteProductEntity favoriteProduct = new FavoriteProductEntity(favoriteProductPK);
        return favoriteProduct;
    }

}
