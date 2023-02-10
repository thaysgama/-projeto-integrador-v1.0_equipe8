package com.dh.digitalbooking.services;

import com.dh.digitalbooking.entities.BookingEntity;
import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.entities.ProductEntity;
import com.dh.digitalbooking.exceptions.BookingNotFoundException;
import com.dh.digitalbooking.exceptions.InvalidDateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookingService<T> {

    T save(T t) throws InvalidDateException;
    T update(T t) throws BookingNotFoundException, InvalidDateException;
    void deleteById(Integer id) throws BookingNotFoundException;
    T findById(Integer id) throws BookingNotFoundException;
    Page<T> findAll(Pageable pageable);
    Page<T> findAllByProduct(ProductEntity product, Pageable pageable);
    Page<BookingEntity> findAllByClient(ClientEntity client, Pageable pageable);

}
