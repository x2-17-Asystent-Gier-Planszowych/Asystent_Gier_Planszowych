package com.asystent.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kamil on 2017-10-17.
 */
@RestController
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;

///https://spring.io/guides/gs/relational-data-access/
        private static final String template = "Logowanie, %s!";
        private final AtomicLong counter = new AtomicLong();

        @RequestMapping("/greeting")
        public String greeting(@RequestParam(value="name", defaultValue="World") String name) {

            jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES ('name','last')");
            if(name.equals("Login")) {
                return new
                        String(template + "succes");
            }else{
                return new
                        String(template + "false");
            }
        }


}
