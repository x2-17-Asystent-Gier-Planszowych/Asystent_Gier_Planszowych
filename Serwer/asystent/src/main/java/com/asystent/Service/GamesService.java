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
    //private final String UPDATE = "UPDATE \"Categories\" SET \"Active\"= false WHERE \"Catname\" = ?";
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
        Integer id = (Integer) jdbcTemplate.queryForObject(SELECT_ID, new Object[] {name}, Integer.class);
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

}
