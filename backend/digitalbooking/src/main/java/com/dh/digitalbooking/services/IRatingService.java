package com.dh.digitalbooking.services;

import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;

public interface IRatingService<T> {

    ProductEntity save(T t) throws ProductNotFoundException;
}
