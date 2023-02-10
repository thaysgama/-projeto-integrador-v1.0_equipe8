package com.dh.digitalbooking.services;

import com.dh.digitalbooking.exceptions.CityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICityService<T> {
    T save(T t);
    T update(T t) throws CityNotFoundException;
    void deleteById(Integer id) throws CityNotFoundException;
    T findById(Integer id) throws CityNotFoundException;
    Page<T> findAll(Pageable pageable);
}
