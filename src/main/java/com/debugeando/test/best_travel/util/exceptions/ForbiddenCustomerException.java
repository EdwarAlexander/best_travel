package com.debugeando.test.best_travel.util.exceptions;

public class ForbiddenCustomerException extends RuntimeException {

    public ForbiddenCustomerException(){
        super("this customer is blocked");
    }
}
