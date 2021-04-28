package com.vyom.expensetrackerapi.repositories;

import com.vyom.expensetrackerapi.domain.User;
import com.vyom.expensetrackerapi.exceptions.EtAuthException;

public interface UserRepositories {

    Integer create(String firstName,String lastName,String email,String password) throws  EtAuthException;

    User findByEmailAndPassword(String email,String password) throws EtAuthException;
    
    Integer getCountByEmail(String email);

    User findById(Integer userId);
    
}
