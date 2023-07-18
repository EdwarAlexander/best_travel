package com.debugeando.test.best_travel.infraestructure.helpers;

import com.debugeando.test.best_travel.util.exceptions.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

    public void isInBlackListCustomer(String customerId){
        if(customerId.equals("BBMB771012HMCRR022")){
            throw new ForbiddenCustomerException();
        }
    }
}
