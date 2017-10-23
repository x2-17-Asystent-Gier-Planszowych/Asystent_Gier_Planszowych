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
