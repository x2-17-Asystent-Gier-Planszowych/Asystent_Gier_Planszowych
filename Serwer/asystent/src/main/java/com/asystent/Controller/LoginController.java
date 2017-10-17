package com.asystent.Controller;

import com.DAO.DaoImpl;
import com.asystent.Model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kamil on 2017-10-17.
 */
@RestController
public class LoginController {

    @Autowired
    JdbcTemplate jdbcTemplate;
    //nie działa to dao trzeba poprawić
   // DaoImpl dao = new DaoImpl();
// Poradnik jak korzystać z jdbcTemplate
///https://spring.io/guides/gs/relational-data-access
    //aby polaczyc się z baza trzeba zmienic nazwe w application.properties bo narazie dałem testowa
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

    @RequestMapping("/login")
    public String login(@RequestParam(value="login", defaultValue="World") String login,
                        @RequestParam(value="haslo", defaultValue="World") String haslo) {
        TestModel testa = new TestModel();
        //INSERT
        //jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES ('name11','last')");
        //SELECT DLA POJEDYNCZEJ DANEJ
        //String test = jdbcTemplate.queryForObject("SELECT first_name FROM customers where id = 11",String.class);
        //Select Listy obiektów
       /* List<TestModel> a = jdbcTemplate.query("select id,first_name,last_name from customers",new RowMapper<TestModel>(){
            @Override
            public TestModel mapRow(ResultSet rs, int rownumber) throws SQLException {
                TestModel e=new TestModel();
                e.setId(rs.getInt(1));
                e.setFirst_name(rs.getString(2));
                e.setLast_name(rs.getString(3));
                return e;
            }
        });*/
       //DELETE tak samo update
        // jdbcTemplate.update("DELETE FROM customers where id = 11");

        //Próba odzielenia dao od controllera
      //  TestModel test = new TestModel(12,"String","String");
       // dao.insertUser(test);

        if(login.equals("Login")) {
            return new
                    String(template + "succes");
        }else{
            return new
                    String(template + "false");
        }
    }


}
