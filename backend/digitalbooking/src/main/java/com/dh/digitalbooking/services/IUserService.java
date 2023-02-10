package com.dh.digitalbooking.services;

import com.dh.digitalbooking.entities.ClientEntity;
import com.dh.digitalbooking.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService<T> {
    String save(T t);
    T update(T t) throws UserNotFoundException;
    void deleteById(Integer id) throws UserNotFoundException;
    T findById(Integer id) throws UserNotFoundException;
    T findByEmail(String email) throws UserNotFoundException;
    ClientEntity findClientById(Integer id) throws UserNotFoundException;
    Page<T> findAll(Pageable pageable);
}
