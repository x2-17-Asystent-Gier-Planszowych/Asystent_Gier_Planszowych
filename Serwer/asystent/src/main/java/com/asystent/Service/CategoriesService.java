package com.asystent.Service;

import com.asystent.Model.Categories;
import com.google.gson.Gson;
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
 * Created by Kamil on 2017-10-21.
 */

@Service
public class CategoriesService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SELECT_ALL = " SELECT * FROM \"Categories\" ";
    private final String SELECT_BY_NAME = " SELECT * FROM \"Categories\" where \"Catname\" = ?";
    private final String INSERT = "INSERT INTO \"Categories\" (\"Catname\",\"Active\") VALUES (?,?)";
    private final String UPDATE = "UPDATE \"Categories\" SET \"Active\"= false WHERE \"Catname\" = ?";
    private Gson gson;

    public List<String> getCats() {

        gson = new Gson();
        List<String> cat = new ArrayList<>();
        List<Categories> a = jdbcTemplate.query(SELECT_ALL, new RowMapper<Categories>() {
            @Override
            public Categories mapRow(ResultSet rs, int rownumber) throws SQLException {
                Categories e = new Categories();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setActive(rs.getBoolean(3));
                return e;
            }
        });

        try {
            for (Categories i : a) {
                cat.add(gson.toJson(i));
            }
        } catch (Exception e) {
            cat.add("{\"id\":0,\"name\":\"\",\"active\":true}");
        }
        return cat;
    }


    public String getCatByName(String name) {

        gson = new Gson();
        String out;

        try {
            Categories cat = jdbcTemplate.queryForObject(SELECT_BY_NAME, new RowMapper<Categories>() {
                @Override
                public Categories mapRow(ResultSet resultSet, int i) throws SQLException {
                    Categories c = new Categories();
                    c.setId(resultSet.getLong(1));
                    c.setName(resultSet.getString(2));
                    c.setActive(resultSet.getBoolean(3));
                    return c;
                }
            }, name);

            try {
                out = gson.toJson(cat);
            } catch (Exception e) {
                return "None";
            }

        } catch (EmptyResultDataAccessException emp) {
            return "404 Not Found";
        }

        return out;
    }


    public String addCat(String name) {
        gson = new Gson();
        List<String> strings = getCats();
        List<Categories> cats = new ArrayList<>();
        for (String a : strings) {
            cats.add(gson.fromJson(a, Categories.class));
        }
        for (Categories a : cats) {
            if (a.getName().equals(name)) {
                return "Duplicated";
            }
        }

        jdbcTemplate.update(INSERT, name, true);
        return "Success";
    }

    public String changeActive(String name){
        jdbcTemplate.update(UPDATE,name );
        return "Success";
    }

}
