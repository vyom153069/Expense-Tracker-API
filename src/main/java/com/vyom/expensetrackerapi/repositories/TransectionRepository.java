package com.vyom.expensetrackerapi.repositories;

import java.util.List;

import com.vyom.expensetrackerapi.domain.Transection;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;

public interface TransectionRepository {

    List<Transection> findAll(Integer userId,Integer categoryId);

    Transection findById(Integer userId,Integer categoryId,Integer transectionId) throws EtResourceNotFoundException;

    Integer create(Integer userId,Integer categoryId,Double amount,String note,Long transectionDate) throws EtBadRequestException;

    void update(Integer userId,Integer categoryId,Integer transectionId,Transection transection) throws EtBadRequestException;

    void removeById(Integer userId,Integer categoryId,Integer transectionId) throws EtResourceNotFoundException;
    
}
