package com.asystent.Service;

import com.asystent.Model.Group;
import com.asystent.Model.GroupWithUsers;
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

    public String getUserInfo(String name,String email){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        jdbcTemplate.query(" SELECT \"Id\",\"Username\",\"Email\",\"About\" FROM \"Users\" WHERE \"Active\"=?  AND \"Username\"=? OR \"Email\"=?",new Object[]{true,name,email}, new RowMapper<User>() {

            @Override
            public User mapRow(ResultSet rs, int rownumber) throws SQLException {

                User user= new User();
                user.setId(rs.getLong(1));
                user.setUsername(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setAbout(rs.getString(4));
                user.setActive(true);
                jsonArray.add(gson.toJsonTree(user));
                return user;
            }
        });
        return  jsonArray.toString();
    }

    public int getId(String login){
        return  jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Users\" WHERE \"Username\"=(?)", new Object[]{login}, Integer.class);
    }

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
       return jdbcTemplate.queryForObject("SELECT \"Password\" FROM \"Users\" where \"Username\" = (?) OR \"Email\" = (?)",new Object[]{login,login}, String.class);
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
        jdbcTemplate.query(" SELECT \"Id\",\"Username\",\"Email\",\"About\" FROM \"Users\" WHERE \"Active\"=?  ORDER BY \"Username\"",new Object[]{true}, new RowMapper<User>() {
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

    public String getGroupsByUser(String name){
        Gson gson = new Gson();
        String query = "select * from \"Groups\" where \"Id\" in (\n" +
                "SELECT \"Group_Id\" FROM \"Group_User\" where \"User_Id\" = \n" +
                "(SELECT \"Id\" from \"Users\" where \"Username\" = ?)\n" +
                ")";
        JsonArray jsonArray = new JsonArray();
        List<Group> a = jdbcTemplate.query(query,new Object[]{name}, new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet rs, int rownumber) throws SQLException {

                Group g = new Group();
                g.setId(rs.getInt(1));
                g.setGroupName(rs.getString(2));
                g.setActive(rs.getBoolean(3));
                jsonArray.add(gson.toJsonTree(g));
                return g;
            }
        });
        return  jsonArray.toString();

    }

    public String getUsersInGroup(String name){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        String query= "select * from \"Users\" where \"Id\" in (\n" +
                "SELECT \"User_Id\" FROM \"Group_User\" where \"Group_Id\" = \n" +
                "( SELECT \"Id\" from \"Groups\" where \"Groupname\" = ?))";
        jdbcTemplate.query(query,new Object[]{name}, new RowMapper<User>() {
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
    }

    public String groupuser(String name){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();

        List<User> listUser = new ArrayList<>();
        String query1 = "select * from \"Groups\" where \"Groupname\" = ?";
        final Group  g = new Group();
                jdbcTemplate.query(query1,new Object[]{name}, new RowMapper<Group>() {
            @Override
            public Group mapRow(ResultSet rs, int rownumber) throws SQLException {

               // g = new Group();
                g.setId(rs.getInt(1));
                g.setGroupName(rs.getString(2));
                g.setActive(rs.getBoolean(3));
                return g;
            }
        });




        String query= "select * from \"Users\" where \"Id\" in (\n" +
                "SELECT \"User_Id\" FROM \"Group_User\" where \"Group_Id\" = \n" +
                "( SELECT \"Id\" from \"Groups\" where \"Groupname\" = ?))";
        jdbcTemplate.query(query,new Object[]{name}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rownumber) throws SQLException {

                User user= new User();
                user.setId(rs.getLong(1));
                user.setUsername(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setAbout(rs.getString(4));
               listUser.add(user);
                return user;
            }
        });

        GroupWithUsers gwu = new GroupWithUsers();
        gwu.setGroupName(g.getGroupName());
        gwu.setId(g.getId());
        gwu.setActive(g.getActive());
        gwu.setList(listUser);

String out = gson.toJson(gwu);
return out;
    }

}
