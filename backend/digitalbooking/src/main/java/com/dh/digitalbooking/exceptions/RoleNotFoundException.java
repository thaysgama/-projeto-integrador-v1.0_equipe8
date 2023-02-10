package com.dh.digitalbooking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFoundException extends Exception{

        public RoleNotFoundException(Integer id) {
            super("Role not found with ID "+id);
        }

}
