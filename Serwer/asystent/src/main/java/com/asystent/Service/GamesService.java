package com.asystent.Service;



import com.asystent.Model.Categories;
import com.asystent.Model.Games;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamil on 2017-10-23.
 */

@Service
public class GamesService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    CategoriesService categoriesService;

    private final String SELECT_ALL = " SELECT * FROM \"Games\" ";
    private final String SELECT_BY_NAME = " SELECT * FROM \"Games\" where \"Name\" = ?";
    private final String SELECT_ID = "SELECT \"Id\" FROM \"Games\" where \"Name\" = ?" ;
    private final String INSERT = "INSERT INTO \"Games\" (\"Name\",\"Active\") VALUES (?,?)";
    private final String INSERT_CAT = "INSERT INTO \"Category_Game\" (\"Category_Id\", \"Game_Id\") VALUES (?,?)";
    private final String SELECT_TAGS = "SELECT * FROM \"Categories\" Where \"Id\" IN (" +
            "SELECT \"Category_Id\" FROM \"Category_Game\" WHERE \"Game_Id\" = ?)" ;
    private final String DELETE_TAGS = "DELETE FROM \"Category_Game\" where \"Game_Id\" = ? ";
    private final String INACTIVE_GAME = "UPDATE \"Games\" SET \"Active\"= false WHERE \"Id\" = ?";
    private Gson gson;


    public String addGame(String name) {
        gson = new Gson();
        if(getGameByName(name).equals("Fail")) {
            jdbcTemplate.update(INSERT, name, true);
            return "Success";
        }
        return "Fail";
    }

    public String getAllGames(){
        gson = new Gson();
        List<String> cat = new ArrayList<>();
        JsonArray jsonArray = new JsonArray();
        List<Games> a = jdbcTemplate.query(SELECT_ALL, new RowMapper<Games>() {
            @Override
            public Games mapRow(ResultSet rs, int rownumber) throws SQLException {
                Games e = new Games();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setActive(rs.getBoolean(3));
                if(rs.getBoolean(3)) {
                    jsonArray.add(gson.toJsonTree(e));
                }
                return e;
            }
        });
        return jsonArray.toString();
    }

    public int getIdByName(String name){
        Integer id;
        try {
            id = (Integer) jdbcTemplate.queryForObject(SELECT_ID, new Object[] {name}, Integer.class);
        } catch (Exception e){
            return 404;
        }
        if(id != null)
            return id;
        else
            return 0;

    }

    public String addTag(String name, String category){
        int gameId = getIdByName(name);
        if(gameId != 0){
        int catId = categoriesService.getIdByName(category);
        try {
        jdbcTemplate.update(INSERT_CAT, catId, gameId);
        } catch (Exception e) {
            return "Fail";
        }

        return "Succes";
        }
        else {
            return "Fail";
        }

    }

    public String getGameTag(String name){
        int gameId = getIdByName(name);
       // List<String> tags = jdbcTemplate.queryForList(SELECT_TAGS, new Object[] { gameId }, String.class);
        JsonArray jsonArray = new JsonArray();
        List<Categories> a = jdbcTemplate.query(SELECT_TAGS, new RowMapper<Categories>() {
            @Override
            public Categories mapRow(ResultSet rs, int rownumber) throws SQLException {
                Categories e = new Categories();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setActive(rs.getBoolean(3));
                if(rs.getBoolean(3)) {
                    jsonArray.add(gson.toJsonTree(e));
                }
                return e;
            }
        },gameId);
        return jsonArray.toString();
    }

    public String getGameByName(String name){

        String out;
        gson = new Gson();
        try {
        Games cat = jdbcTemplate.queryForObject(SELECT_BY_NAME, new RowMapper<Games>() {
            @Override
            public Games mapRow(ResultSet resultSet, int i) throws SQLException {
                Games c = new Games();
                c.setId(resultSet.getLong(1));
                c.setName(resultSet.getString(2));
                c.setActive(resultSet.getBoolean(3));
                return c;
            }
        }, name);


        try {
            if(cat.isActive()) {
                out = gson.toJson(cat);
                return out;
            }
            out = "Fail";
        } catch (Exception e) {
            return "Fail";
        }

    } catch (EmptyResultDataAccessException emp) {
        return "Fail";
    }

        return out;
}

    public String deleteGame(String name){
        int id = getIdByName(name);
        try {
            jdbcTemplate.update(DELETE_TAGS, id);
            jdbcTemplate.update(INACTIVE_GAME, id);
        } catch (Exception e){
            return "Fail";
        }
        return "Success";
    }
}
