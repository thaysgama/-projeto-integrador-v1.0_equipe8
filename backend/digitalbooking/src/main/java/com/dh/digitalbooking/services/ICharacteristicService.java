package com.dh.digitalbooking.services;

import com.dh.digitalbooking.exceptions.CharacteristicNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICharacteristicService<T> {
    T save(T t);
    T update(T t) throws CharacteristicNotFoundException;
    void deleteById(Integer id) throws CharacteristicNotFoundException;
    T findById(Integer id) throws CharacteristicNotFoundException;
    T findByIdOrNull(Integer id);
    Page<T> findAll(Pageable pageable);
}
