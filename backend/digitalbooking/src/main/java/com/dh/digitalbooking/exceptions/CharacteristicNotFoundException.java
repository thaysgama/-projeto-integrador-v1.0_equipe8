package com.dh.digitalbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CharacteristicNotFoundException extends Exception {

    public CharacteristicNotFoundException(Integer id) {
        super("Characteristic not found with ID "+id);
    }
}
