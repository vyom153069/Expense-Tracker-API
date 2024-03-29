package com.vyom.expensetrackerapi.resources;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.SpringLayout.Constraints;

import com.vyom.expensetrackerapi.services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.vyom.expensetrackerapi.Constants;
import com.vyom.expensetrackerapi.domain.*;

@RestController
@RequestMapping("/api/users")
public class UserResources {

    @Autowired
    UserServices userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> userMap){
        String email=(String)userMap.get("email");
        String password=(String)userMap.get("password");
        User user=userService.validateUser(email, password);
       
        return new ResponseEntity<>(generateJWTToken(user),HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String,Object>userMap){
        String firstName=(String)userMap.get("firstName");
        String lastName=(String)userMap.get("lastName");
        String email=(String)userMap.get("email");
        String password=(String)userMap.get("password");

        User user=userService.registeUser(firstName, lastName, email, password);
        return new ResponseEntity<>(generateJWTToken(user),HttpStatus.OK);
    }

    private Map<String,String> generateJWTToken(User user){
        long timeStamp=System.currentTimeMillis();
        String token=Jwts.builder()
                            .signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                            .setIssuedAt(new Date(timeStamp))
                            .setExpiration(new Date(timeStamp+Constants.TOKEN_VALIDITY))
                            .claim("userId",user.getUserId())
                            .claim("firstName",user.getFirstName())
                            .claim("lastName",user.getLastName())
                            .claim("email",user.getEmail())
                            .compact();
        Map<String,String> map=new HashMap<>();
        map.put("token",token);
        return map;
    }
}
