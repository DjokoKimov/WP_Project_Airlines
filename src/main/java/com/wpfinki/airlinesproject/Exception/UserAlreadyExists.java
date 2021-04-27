package com.wpfinki.airlinesproject.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;


public class UserAlreadyExists extends RuntimeException{
    public UserAlreadyExists(String message){
        super(message);
    }
}
