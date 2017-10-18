//package com.DAO;
//
//import com.asystent.Model.TestModel;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//
///**
// * Created by kriscool on 17.10.2017.
// */
//public class DaoImpl {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    public void insertUser(TestModel test){
//        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES ('?','?')",test.getFirst_name(),test.getLast_name());
//    }
//}
//
