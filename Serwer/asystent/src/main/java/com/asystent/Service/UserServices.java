package com.asystent.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by kriscool on 24.10.2017.
 */
@Service
public class UserServices {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int deactive(String login){
        return  jdbcTemplate.update("UPDATE \"Users\" SET \"Active\" = ? where \"Username\" = ?",new Object[]{false,login});
    }
    public int updateAbout(String login,String about){
        return  jdbcTemplate.update("UPDATE \"Users\" SET \"About\" = ? where \"Username\" = ?",new Object[]{about,login});
    }
    public int updateEmail(String login,String email){
        return jdbcTemplate.update("UPDATE \"Users\" SET \"Email\" = ? where \"Username\" = ?",new Object[]{email,login});
    }
    public int updatePassword(String login,String password){
        return jdbcTemplate.update("UPDATE \"Users\" SET \"Password\" = ? where \"Username\" = ?",new Object[]{password,login});
    }
    public String getPassword(String login){
       return jdbcTemplate.queryForObject("SELECT \"Password\" FROM \"Users\" where \"Username\" = (?)",new Object[]{login}, String.class);
    }

    public Boolean isUsedUsername(String name){
        String sqlForUserName = 	"SELECT EXISTS(SELECT 1 FROM \"Users\" WHERE  \"Username\" = ?)";
        Boolean isUsedUserName = jdbcTemplate.queryForObject(sqlForUserName, new Object[] {name}, Boolean.class);
        return isUsedUserName;
    }

    public Boolean isEmailUse(String email){
        String sqlForEmail = 	"SELECT EXISTS(SELECT 1 FROM \"Users\" WHERE  \"Email\" = ?)";
        Boolean isUsedEmail = jdbcTemplate.queryForObject(sqlForEmail, new Object[] {email}, Boolean.class);
        return  isUsedEmail;
    }
    public int insertUser(String email,String passwd,String login){
        return jdbcTemplate.update("INSERT INTO \"Users\" (\"Email\", \"Username\",\"Password\") VALUES (?,?,?)",new Object[]{email,login,passwd});
    }

}
