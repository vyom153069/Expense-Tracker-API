package com.vyom.expensetrackerapi.services;

import java.util.regex.Pattern;

import com.vyom.expensetrackerapi.domain.User;
import com.vyom.expensetrackerapi.exceptions.EtAuthException;
import com.vyom.expensetrackerapi.repositories.UserRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServicesImpl implements UserServices {

    @Autowired
    UserRepositories userRepository;

    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        // TODO Auto-generated method stub
        if(email !=null)email=email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registeUser(String firstName, String lastName, String email, String password) throws EtAuthException {
        // TODO Auto-generated method stub

        Pattern pattern=Pattern.compile("^(.+)@(.+)$");
        if(email !=null ) email=email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new EtAuthException("Invalid email formate");
        Integer count=userRepository.getCountByEmail(email);
        if(count>0)
            throw new EtAuthException("Email already in use");

        Integer userId=userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }
    
}
