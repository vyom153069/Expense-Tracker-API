package com.vyom.expensetrackerapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;



import com.vyom.expensetrackerapi.domain.User;
import com.vyom.expensetrackerapi.exceptions.EtAuthException;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoriesImpl implements UserRepositories {

    private static final String  SQL_CREATE="INSERT INTO ET_USER(USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) VALUES(NEXTVAL('ET_USER_SEQ'),?,?,?,?)";
    private static final String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM ET_USER WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID="SELECT USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD FROM ET_USER WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_EMAIL="SELECT USER_ID,FIRST_NAME,LAST_NAME,EMAIL,PASSWORD FROM ET_USER WHERE EMAIL=?";

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Override
    public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
        // TODO Auto-generated method stub
        String hashedPassword=BCrypt.hashpw(password, BCrypt.gensalt(10));
        try{

            KeyHolder keyHolder=new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps=connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3,email);
                ps.setString(4, hashedPassword);
                return ps;
            },keyHolder);
            return (Integer)keyHolder.getKeys().get("USER_ID");
        }catch(Exception e){
            throw new EtAuthException("Invalid details, Failed to create account");
        }
        
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        // TODO Auto-generated method stub
        try{
            User user= jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
            if(!BCrypt.checkpw(password,user.getPassword()))
                throw new EtAuthException("Invalid email/password");
            return user;
        }catch(EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid email/password");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        // TODO Auto-generated method stub
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email},Integer.class);
    }

    @Override
    public User findById(Integer userId) {
        // TODO Auto-generated method stub
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId},userRowMapper);
    }

    private RowMapper<User> userRowMapper= ( (rs,rowNum) -> {
        return  new User(
            rs.getInt("USER_ID"),
            rs.getString("FIRST_NAME"),
            rs.getString("LAST_NAME"),
            rs.getString("EMAIL"),
            rs.getString("PASSWORD")
        );
    });
    
}
