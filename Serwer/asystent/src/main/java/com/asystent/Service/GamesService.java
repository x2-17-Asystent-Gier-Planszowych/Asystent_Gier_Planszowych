package com.asystent.Service;



import com.asystent.Model.Games;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final String SELECT_TAGS = "SELECT \"Catname\" FROM \"Categories\" Where \"Id\" IN (" +
            "SELECT \"Category_Id\" FROM \"Category_Game\" WHERE \"Game_Id\" = ?)" ;
    private final String DELETE_TAGS = "DELETE FROM \"Category_Game\" where \"Game_Id\" = ? ";
    private final String INACTIVE_GAME = "UPDATE \"Games\" SET \"Active\"= false WHERE \"Id\" = ?";
    private Gson gson;


    public String addGame(String name) {
        gson = new Gson();
        jdbcTemplate.update(INSERT, name, true);
        return "Success";
    }

    public List<String> getAllGames(){
        gson = new Gson();
        List<String> cat = new ArrayList<>();
        List<Games> a = jdbcTemplate.query(SELECT_ALL, new RowMapper<Games>() {
            @Override
            public Games mapRow(ResultSet rs, int rownumber) throws SQLException {
                Games e = new Games();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setActive(rs.getBoolean(3));
                return e;
            }
        });

        try {
            for (Games i : a) {
                cat.add(gson.toJson(i));
            }
        } catch (Exception e) {
            cat.add("{\"id\":0,\"name\":\"\",\"active\":true}");
        }
        return cat;
    }

    public int getIdByName(String name){
        Integer id;
        try {
            id = (Integer) jdbcTemplate.queryForObject(SELECT_ID, new Object[] {name}, Integer.class);
        } catch (Exception e){
            return 404;
        }

        return id;
    }

    public String addTag(String name, String category){
        int gameId = getIdByName(name);
        int catId = categoriesService.getIdByName(category);
        try {
        jdbcTemplate.update(INSERT_CAT, catId, gameId);
        } catch (Exception e) {
            return "Fail";
        }

        return "Succes";

    }

    public List<String> getGameTag(String name){
        int gameId = getIdByName(name);
        List<String> tags = jdbcTemplate.queryForList(SELECT_TAGS, new Object[] { gameId }, String.class);
        return tags;
    }

    public String getGameByName(String name){

        String out;
        gson = new Gson();
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

        try{
            out = gson.toJson(cat);
        } catch (Exception e){
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
