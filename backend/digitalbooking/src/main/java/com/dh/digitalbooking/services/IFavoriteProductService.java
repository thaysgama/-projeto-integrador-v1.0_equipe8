package com.dh.digitalbooking.services;

import com.dh.digitalbooking.entities.UserEntity;

public interface IFavoriteProductService<T> {

    UserEntity save(T t);
}
