package com.asystent.Controller;

import com.asystent.Model.Categories;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Kamil on 2017-10-18.
 */
@RestController
public class CategoriesController {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public String getCat(String name){
        String SELECT = " SELECT * FROM \"Categories\" ";

        Gson gson = new Gson();


        List<Categories> a = jdbcTemplate.query(SELECT,new RowMapper<Categories>(){
            @Override
            public Categories mapRow(ResultSet rs, int rownumber) throws SQLException {
                Categories e=new Categories();
                e.setId(rs.getLong(1));
                e.setName(rs.getString(2));
                e.setActive(rs.getBoolean(3));
                return e;
            }
        });

        String cat;
        try {
            cat = gson.toJson(a.get(0));
        } catch (Exception  e) {
            return "{\"id\":0,\"name\":\"\",\"active\":true}";
        }
        return cat;
    }


    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String getCategories(@RequestParam(value="name", defaultValue="World") String name) {
        return getCat(name);
    }

    @RequestMapping(value = "/categories" , method = RequestMethod.POST)
    public String addCategory(@RequestParam(value="name") String name){

        Gson gson = new Gson();
        Categories cat = gson.fromJson(getCat(name), Categories.class);
       if(!cat.getName().equals(name)) {
            String query = "INSERT INTO \"Categories\" (\"Catname\",\"Active\") VALUES (?,?)";
            jdbcTemplate.update(
                    query,
                    name, true
            );
        } else {
            return "Duplication";
        }

        return "Success";
    }

}
