package com.asystent.Service;

import com.asystent.Model.Group;
import com.asystent.Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kriscool on 24.10.2017.
 */
@Service
public class UserServices {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int deactive(String login){
        int idUser = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Users\" WHERE \"Username\"=(?)", new Object[]{login}, Integer.class);
        jdbcTemplate.update("DELETE FROM \"Group_User\" where \"User_Id\"=?",new Object[]{idUser});
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
        return jdbcTemplate.update("INSERT INTO \"Users\" (\"Email\", \"Username\",\"Password\",\"Active\") VALUES (?,?,?,?)",new Object[]{email,login,passwd,true});
    }

    public String getAllUsers(){

        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        jdbcTemplate.query(" SELECT \"Id\",\"Username\",\"Email\",\"About\" FROM \"Users\" WHERE \"Active\"=? ",new Object[]{true}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rownumber) throws SQLException {

                User user= new User();
                user.setId(rs.getLong(1));
                user.setUsername(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setAbout(rs.getString(4));
                jsonArray.add(gson.toJsonTree(user));
                return user;
            }
        });

        return  jsonArray.toString();
    };

}
