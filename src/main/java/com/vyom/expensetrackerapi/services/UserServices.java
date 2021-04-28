package com.vyom.expensetrackerapi.services;

import com.vyom.expensetrackerapi.domain.User;
import com.vyom.expensetrackerapi.exceptions.EtAuthException;

public interface UserServices {
    User validateUser(String email,String password) throws EtAuthException;
    User registeUser(String firstName,String lastName,String email,String passowrd) throws EtAuthException;
}
