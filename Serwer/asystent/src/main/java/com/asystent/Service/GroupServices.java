package com.asystent.Service;

import com.asystent.Model.Games;
import com.asystent.Model.Group;
import com.google.gson.*;
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
public class GroupServices {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserServices userServices;

    public String allGroup() {
        Gson gson = new Gson();
        List<String> cat = new ArrayList<>();
        JsonArray jsonArray = new JsonArray();
        List<Group> a = jdbcTemplate.query(" SELECT \"Id\",\"Groupname\",\"Active\" FROM \"Groups\" WHERE \"Active\"=? ORDER BY \"Groupname\" ",new Object[]{true}, new RowMapper<Group>() {
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

    public int addUserToGroupByName(String nameUser,String nameGroup){
        int nameUserLocal = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Users\" WHERE \"Username\"=(?)", new Object[]{nameUser}, Integer.class);
        int nameGroupLocal = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Groups\" WHERE \"Groupname\"=(?)", new Object[]{nameGroup}, Integer.class);
        return jdbcTemplate.update("INSERT INTO \"Group_User\"(\"Group_Id\", \"User_Id\") VALUES (?,?)",new Object[]{nameGroupLocal,nameUserLocal});
    }

    public int addUserToGroup(int idUser,int idGroup){
        return jdbcTemplate.update("INSERT INTO \"Group_User\"(\"Group_Id\", \"User_Id\") VALUES (?,?)",new Object[]{idGroup,idUser});
    }

    public String getUserInGroup(int idGroup){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        List<Integer> tabId = jdbcTemplate.query(" SELECT \"User_Id\" FROM \"Group_User\" WHERE \"Group_Id\"=(?)",new Object[]{idGroup}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rownumber) throws SQLException {
                return rs.getInt(1);
            }
        });
        List<String> nameUser = new ArrayList<String>();

        for(int a : tabId) {
            JsonObject json = new JsonObject();


            json.addProperty("name",jdbcTemplate.queryForObject(" SELECT \"Username\" FROM \"Users\" WHERE \"Id\"=(?)", new Object[]{a}, String.class));
            jsonArray.add(json);
        }

        return  jsonArray.toString();
    }


    public String getUserInGroupByNameGroup(String name){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        int idGroup = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Groups\" WHERE \"Groupname\"=(?)", new Object[]{name}, Integer.class);

        List<Integer> tabId = jdbcTemplate.query(" SELECT \"User_Id\" FROM \"Group_User\" WHERE \"Group_Id\"=(?)",new Object[]{idGroup}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rownumber) throws SQLException {
                return rs.getInt(1);
            }
        });
        List<String> nameUser = new ArrayList<String>();

        for(int a : tabId) {
            JsonObject json = new JsonObject();


            json.addProperty("login",jdbcTemplate.queryForObject(" SELECT \"Username\" FROM \"Users\" WHERE \"Id\"=(?)", new Object[]{a}, String.class));
            jsonArray.add(json);
        }

        return  jsonArray.toString();

    }

    public int addGroup(String nameGroup, String owner){
        int userId = userServices.getId(owner);
        return jdbcTemplate.update("INSERT INTO \"Groups\"(\"Groupname\",\"Active\",\"Owner\") VALUES (?,?,?)",new Object[]{nameGroup,true,userId});
    }

    public int deleteGroup(String nameGroup){

        int idGroup = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Groups\" WHERE \"Groupname\"=(?)", new Object[]{nameGroup}, Integer.class);
        jdbcTemplate.update("DELETE FROM \"Group_User\" where \"Group_Id\"=?",new Object[]{idGroup});
        return jdbcTemplate.update("UPDATE \"Groups\" SET \"Active\"=? WHERE \"Groupname\"=?",new Object[]{false,nameGroup});
    }

    public int deleteUserFromGroup(String nameUser,String nameGroup){
        int idGroup = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Groups\" WHERE \"Groupname\"=(?)", new Object[]{nameGroup}, Integer.class);
        int idUser = jdbcTemplate.queryForObject(" SELECT \"Id\" FROM \"Users\" WHERE \"Username\"=(?)", new Object[]{nameUser}, Integer.class);
        return jdbcTemplate.update("DELETE FROM \"Group_User\" where \"Group_Id\"=? AND \"User_Id\"=?",new Object[]{idGroup,idUser});

    }


}
