package com.vyom.expensetrackerapi.services;

import java.util.List;

import com.vyom.expensetrackerapi.domain.Transection;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;
import com.vyom.expensetrackerapi.repositories.TransectionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class TransectionServiceImpl implements TransectionService{

    @Autowired
    TransectionRepository transectionRepository;

    @Override
    public List<Transection> fetchAllTransections(Integer userId, Integer categoryId) {
        // TODO Auto-generated method stub
        return transectionRepository.findAll(userId,categoryId);
    }

    @Override
    public Transection fetchTransactionById(Integer userId, Integer categoryId, Integer transectionId)
            throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        return transectionRepository.findById(userId, categoryId, transectionId);
    }

    @Override
    public Transection addTransection(Integer userId, Integer categoryId, Double amount, String note,
            Long transectionDate) throws EtBadRequestException {
        // TODO Auto-generated method stub
        int transectionId=transectionRepository.create(userId, categoryId, amount, note, transectionDate);
        return transectionRepository.findById(userId, categoryId, transectionId);
    }

    @Override
    public void updateTransection(Integer userId, Integer categoryId, Integer transactionId, Transection transaction) throws EtBadRequestException {
        transectionRepository.update(userId, categoryId, transactionId, transaction);
    }

    @Override
    public void removeTransection(Integer userId, Integer categoryId, Integer transectionId)
            throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        transectionRepository.removeById(userId, categoryId, transectionId);
        
    }
    
}
