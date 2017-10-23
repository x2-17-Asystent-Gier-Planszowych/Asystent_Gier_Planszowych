package com.asystent.Controller;


import com.asystent.Model.Categories;
import com.asystent.Model.TestModel;
import com.asystent.Model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kamil on 2017-10-17.
 */
@RestController
public class UserController {

    @Autowired
    JdbcTemplate jdbcTemplate;

        
        
        @RequestMapping("/rejestacja")
        public String rejestacja(@RequestParam(value="name", defaultValue="World") String name,
        		@RequestParam(value="email", defaultValue="World") String email,
        		@RequestParam(value="password", defaultValue="World") String password) {
        	
        	String sqlForUserName = 	"SELECT EXISTS(SELECT 1 FROM \"Users\" WHERE  \"Username\" = ?)";
        	Boolean isUsedUserName = jdbcTemplate.queryForObject(sqlForUserName, new Object[] {name}, Boolean.class);
        	
        	String sqlForEmail = 	"SELECT EXISTS(SELECT 1 FROM \"Users\" WHERE  \"Email\" = ?)";
        	Boolean isUsedEmail = jdbcTemplate.queryForObject(sqlForEmail, new Object[] {email}, Boolean.class);
        	
        	
        	
        	 if(isUsedUserName == false) {
        		 int updateSucces =jdbcTemplate.update("INSERT INTO \"Users\" (\"Email\", \"Username\",\"Password\") VALUES (?,?,?)",new Object[]{email,name,password});
        		    if(updateSucces!=0) {
        		    	return new String("Succes");
		            }else{
		                 return new  String("Failed");
		            }
        		
             }else if(isUsedEmail == true) {
            	 return new
                         String("Email");
             }else{
            	 return new
                         String("Username");
             }
        	 
        	 
        
        }
  //  http://localhost:8080/login?login=Login&haslo=haslo
    @RequestMapping("/login")
    public String login(@RequestParam(value="login", defaultValue="World") String login,
                        @RequestParam(value="haslo", defaultValue="World") String haslo) {
    	
    	String sqlForUserName = 	"SELECT EXISTS(SELECT 1 FROM \"Users\" WHERE  \"Username\" = ?)";
    	Boolean isUsedUserName = jdbcTemplate.queryForObject(sqlForUserName, new Object[] {login}, Boolean.class);
    	String password = null;
    	
    	if(isUsedUserName == true){
    	password = jdbcTemplate.queryForObject("SELECT \"Password\" FROM \"Users\" where \"Username\" = (?)",new Object[]{login}, String.class);
    	}else{
    		password = "Mismatch login";
    	}
    	
       if(haslo.equals(password)) {
            return "Succes";
        }else{
            return "Failed";
        }
    	
    }

	@RequestMapping("/changePassword")
	public String changePassword(@RequestParam(value="login", defaultValue="World") String login,
						@RequestParam(value="haslo", defaultValue="World") String haslo) {
		int updateSucces = jdbcTemplate.update("UPDATE \"Users\" SET \"Password\" = ? where \"Username\" = ?",new Object[]{haslo,login});
		if(updateSucces!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}

	@RequestMapping("/changeAbout")
	public String changeAbout(@RequestParam(value="about", defaultValue="World") String aboutDescription,
						   @RequestParam(value="login", defaultValue="World") String login) {
		int updateSucces = jdbcTemplate.update("UPDATE \"Users\" SET \"About\" = ? where \"Username\" = ?",new Object[]{aboutDescription,login});
		if(updateSucces!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}

	@RequestMapping("/deactivate")
	public String deactivate(@RequestParam(value="deactivate", defaultValue="World") Boolean deactivate,
							 @RequestParam(value="login", defaultValue="World") String login
							 ) {
		int updateSucces = jdbcTemplate.update("UPDATE \"Users\" SET \"Active\" = ? where \"Username\" = ?",new Object[]{deactivate,login});
		if(updateSucces!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}

	@RequestMapping("/changeEmail")
	public String changeEmail(@RequestParam(value="deactivate", defaultValue="World") String email,
							  @RequestParam(value="login", defaultValue="World") String login) {
		int updateSucces = jdbcTemplate.update("UPDATE \"Users\" SET \"Email\" = ? where \"Username\" = ?",new Object[]{email,login});
		if(updateSucces!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}
}
//nie działa to dao trzeba poprawić
// DaoImpl dao = new DaoImpl();
//Poradnik jak korzystać z jdbcTemplate
///https://spring.io/guides/gs/relational-data-access
//aby polaczyc się z baza trzeba zmienic nazwe w application.properties bo narazie dałem testowa
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

