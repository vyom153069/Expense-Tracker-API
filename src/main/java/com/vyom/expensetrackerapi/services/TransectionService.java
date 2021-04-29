package com.vyom.expensetrackerapi.services;

import java.util.List;

import com.vyom.expensetrackerapi.domain.Transection;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;

public interface TransectionService {
    
    List<Transection> fetchAllTransections(Integer userId,Integer categoryId);
    
    Transection fetchTransactionById(Integer userId,Integer categoryId,Integer transectionId) throws EtResourceNotFoundException;

    Transection addTransection(Integer userId,Integer categoryId,Double amount,String note,Long transectionDate) throws EtBadRequestException;

    void updateTransection(Integer userId,Integer categoryId,Integer transectionId,Transection transection) throws EtBadRequestException;

    void removeTransection(Integer userId,Integer categoryId,Integer transectionId) throws EtResourceNotFoundException;

}
