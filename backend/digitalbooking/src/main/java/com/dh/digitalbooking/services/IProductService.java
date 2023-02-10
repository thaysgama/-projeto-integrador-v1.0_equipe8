package com.dh.digitalbooking.services;

import com.dh.digitalbooking.entities.*;
import com.dh.digitalbooking.exceptions.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IProductService<T> {
    T save(T t);
    T update(T t) throws ProductNotFoundException;
    void deleteById(Integer id) throws ProductNotFoundException;
    T findById(Integer id) throws ProductNotFoundException;
    Page<T> findAll(Pageable pageable);
    Page<ImageEntity> findAllProductImages(Integer id, Pageable pageable) throws ProductNotFoundException;
    Page<T> findAllByCity(CityEntity city, Pageable pageable);
    Page<T> findAllByCategory(CategoryEntity category, Pageable pageable);
    Page<T> findAllByCityAndCategory(CityEntity city, CategoryEntity category, Pageable pageable);
    Page<T> findAllByDate(LocalDate checkIn, LocalDate checkOut, Pageable pageable);
    Page<T> findAllByCityAndDate(Integer cityId, LocalDate checkIn, LocalDate checkOut, Pageable pageable);
    Page<T> findAllByProprietor(ProprietorEntity proprietor, Pageable pageable);
}
