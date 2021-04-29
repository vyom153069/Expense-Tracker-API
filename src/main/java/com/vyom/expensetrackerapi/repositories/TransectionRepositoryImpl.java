package com.vyom.expensetrackerapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;


import com.vyom.expensetrackerapi.domain.Transection;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class TransectionRepositoryImpl implements TransectionRepository {

    private static final String SQL_CREATE="INSERT INTO ET_TRANSACTION(TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE) VALUES(NEXTVAL('ET_TRANSACTION_SEQ'),?,?,?,?,?)";

    private static final String SQL_FIND_BY_ID="SELECT TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE FROM ET_TRANSACTION WHERE USER_ID=? AND CATEGORY_ID=? AND TRANSACTION_ID=? ";

    private static final String SQL_ALL="SELECT TRANSACTION_ID,CATEGORY_ID,USER_ID,AMOUNT,NOTE,TRANSACTION_DATE FROM ET_TRANSACTION WHERE USER_ID=? AND CATEGORY_ID=? ";

    private static final String SQL_UPDATE = "UPDATE ET_TRANSACTION SET AMOUNT = ?, NOTE = ?, TRANSACTION_DATE = ? WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    
    private static final String SQL_DELETE = "DELETE FROM ET_TRANSACTION WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ?";
    
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public List<Transection> findAll(Integer userId, Integer categoryId) {
        // TODO Auto-generated method stub
        return jdbcTemplate.query(SQL_ALL, new Object[]{userId,categoryId},transectionRowMapper);
    }

    @Override
    public Transection findById(Integer userId, Integer categoryId, Integer transectionId)
            throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId, transectionId},transectionRowMapper);

        }catch(Exception e){
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transectionDate)
            throws EtBadRequestException {
        // TODO Auto-generated method stub
        try{
            KeyHolder keyHolder=new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps=connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, categoryId);
                ps.setInt(2,userId);
                ps.setDouble(3, amount);
                ps.setString(4,note);
                ps.setLong(5,transectionDate);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }catch(Exception e){
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transectionId, Transection transaction)
            throws EtBadRequestException {
        // TODO Auto-generated method 
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{transaction.getAmount(), transaction.getNote(), transaction.getTransectionDate() ,userId, categoryId, transectionId});
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
        
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transectionId)
            throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userId, categoryId, transectionId});
        if(count == 0)
            throw new EtResourceNotFoundException("Transaction not found");
        
    }

    private RowMapper<Transection> transectionRowMapper=((rs,rowNum)->{
        return new Transection(rs.getInt("TRANSACTION_ID"),
                                rs.getInt("CATEGORY_ID"),
                                rs.getInt("USER_ID"),
                                rs.getDouble("AMOUNT"),
                                rs.getString("NOTE"),
                                rs.getLong("TRANSACTION_DATE"));
    });
    
}
