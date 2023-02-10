package com.dh.digitalbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends Exception {

    public CategoryNotFoundException(Integer id) {
        super("Category not found with ID "+id);
    }
}
