package com.foft.microservicefiche.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImpossibleToAdd extends RuntimeException{

    public  ImpossibleToAdd (String message){super(message);}

}
