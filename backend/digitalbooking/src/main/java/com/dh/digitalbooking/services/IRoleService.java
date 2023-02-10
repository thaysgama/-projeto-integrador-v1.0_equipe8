package com.dh.digitalbooking.services;

import com.dh.digitalbooking.exceptions.RoleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService<T> {
    T save(T t);
    T update(T t) throws RoleNotFoundException;
    void deleteById(Integer id) throws RoleNotFoundException;
    T findById(Integer id) throws RoleNotFoundException;
    Page<T> findAll(Pageable pageable);
}
