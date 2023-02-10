package com.dh.digitalbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(Integer id) {
        super("Booking not found with ID "+id);
    }

}
