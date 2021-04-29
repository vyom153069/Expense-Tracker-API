package com.vyom.expensetrackerapi.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.http.HttpServletRequest;

import com.vyom.expensetrackerapi.domain.Transection;
import com.vyom.expensetrackerapi.services.TransectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories/{categoryId}/transactions")
public class TransectionResource {

    @Autowired
    TransectionService transectionService;
    
    @GetMapping("{transactionId}")
    public ResponseEntity<Transection> getTransactionById(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId,
                                                            @PathVariable("transactionId") Integer transactionId){

            int userId=(Integer)request.getAttribute("userId");
            Transection transection=transectionService.fetchTransactionById(userId, categoryId, transactionId);
            return new ResponseEntity<>(transection,HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Transection>> getAllTransactions(HttpServletRequest request,@PathVariable("categoryId")Integer categoryId){

        int userId=(Integer)request.getAttribute("userId");
        List<Transection> transactions=transectionService.fetchAllTransections(userId, categoryId);
        return new ResponseEntity<>(transactions,HttpStatus.OK);
        
    }

    @PostMapping("")
    public ResponseEntity<Transection> addTransection(HttpServletRequest request,@PathVariable("categoryId") Integer categoryId,
                                                        @RequestBody Map<String,Object> transectionMap){
                                                            

            int userId=(Integer)request.getAttribute("userId");
            Double amount=Double.valueOf(transectionMap.get("amount").toString());
            String note=(String)transectionMap.get("note");
            Long transectionDate=(Long) transectionMap.get("transactionDate");
            Transection transection=transectionService.addTransection(userId, categoryId, amount, note, transectionDate);
            return new ResponseEntity<>(transection,HttpStatus.CREATED);
    }

   


    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Integer categoryId,
                                                                  @PathVariable("transactionId") Integer transactionId,
                                                                  @RequestBody Transection transaction) {
        int userId = (Integer) request.getAttribute("userId");
        transectionService.updateTransection(userId, categoryId, transactionId, transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
                                                                  @PathVariable("categoryId") Integer categoryId,
                                                                  @PathVariable("transactionId") Integer transactionId) {
        int userId = (Integer) request.getAttribute("userId");
        transectionService.removeTransection(userId, categoryId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
}
