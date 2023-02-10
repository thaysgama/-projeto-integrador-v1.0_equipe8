package com.dh.digitalbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends Exception{
    public ImageNotFoundException(Integer id) {
        super("Image not found with ID "+id);
    }
}
