package com.vyom.expensetrackerapi.repositories;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.List;

import com.vyom.expensetrackerapi.domain.Category;
import com.vyom.expensetrackerapi.exceptions.EtBadRequestException;
import com.vyom.expensetrackerapi.exceptions.EtResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private static final String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION, " +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTION T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID = ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";
    private static final String SQL_CREATE = "INSERT INTO ET_CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) VALUES(NEXTVAL('ET_CATEGORIES_SEQ'), ?, ?, ?)";


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Category> findAll(Integer userId) throws EtResourceNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Category findById(Integer userId, Integer categoryId) throws EtResourceNotFoundException {
       try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId,categoryId},categoryRowMapper);
       }catch(Exception e){
           throw new EtResourceNotFoundException("Category not found");
       }
    }

    @Override
    public Integer create(Integer userId, String title, String description) throws EtBadRequestException {
        // TODO Auto-generated method stub
        try{
            KeyHolder keyHolder=new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
              PreparedStatement ps=connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
              ps.setInt(1, userId);
              ps.setString(2, title);
              ps.setString(3, description);
              return ps;
            }, keyHolder);

            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");

        }catch(Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void removeById(Integer userId, Integer categoryId) {
        // TODO Auto-generated method stub
        
    }

    private RowMapper<Category> categoryRowMapper=((rs,rowNum)->{
        return new Category(rs.getInt("CATEGORY_ID"),rs.getInt("USER_ID"),rs.getString("TITLE"),rs.getString("DESCRIPTION"),rs.getDouble("TOTAL_EXPENSE"));
    });
}