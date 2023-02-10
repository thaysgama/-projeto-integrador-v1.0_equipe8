package com.dh.digitalbooking.services;

import com.dh.digitalbooking.exceptions.CategoryNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICategoryService<T> {
    T save(T t);
    T update(T t) throws CategoryNotFoundException;
    void deleteById(Integer id) throws CategoryNotFoundException ;
    T findById(Integer id) throws CategoryNotFoundException;
    Page<T> findAll(Pageable pageable);
}
