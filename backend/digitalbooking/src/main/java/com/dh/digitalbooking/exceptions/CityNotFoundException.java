package com.dh.digitalbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CityNotFoundException extends Exception{
    public CityNotFoundException(Integer id) {
        super("City not found with ID "+id);
    }
}
