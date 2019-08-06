package com.webservices.sample;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public @interface Test {

    @WebMethod
    String[] getTest();

    @WebMethod
    boolean addOrder(String Test);

}
