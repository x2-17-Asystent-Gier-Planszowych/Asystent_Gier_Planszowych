package com.asystent.Controller;


import com.asystent.Model.Categories;
import com.asystent.Model.User;

import com.asystent.Service.UserServices;
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
	UserServices userServices;




	@RequestMapping("/get/user/info")
	public String getUserInfo(@RequestParam(value="name",defaultValue="World") String name,
							 @RequestParam(value="email",defaultValue="World") String email) {

			return userServices.getUserInfo(name, email);


	}
    @RequestMapping("/get/users")
	public String getUsers() {
		return userServices.getAllUsers();
	}


        @RequestMapping("/registration")
        public String rejestacja(@RequestParam(value="name") String name,
        		@RequestParam(value="email") String email,
        		@RequestParam(value="password") String password) {
			if(!(email.isEmpty() || name.isEmpty() || password.isEmpty())) {
				if (!userServices.isUsedUsername(name)) {
					int updateSucces = userServices.insertUser(email, password, name);
					if (updateSucces != 0) {
						return new String("Succes");
					} else {
						return new String("Failed");
					}

				} else if (userServices.isEmailUse(email)) {
					return new
							String("Email");
				} else {
					return new
							String("Username");
				}
			}else{
				return "Brak danych";
			}

        
        }
  	// http://localhost:8080/login?login=Login&haslo=haslo
    @RequestMapping("/signin")
    public String login(@RequestParam(value="login", defaultValue="World") String login,
                        @RequestParam(value="haslo", defaultValue="World") String passwordFromAplication) {

    	String password = null;
    	if(userServices.isUsedUsername(login) == true || userServices.isEmailUse(login) == true){
    		password = userServices.getPassword(login);
    	}else{
    		password = "Mismatch login";
    	}
    	
       if(passwordFromAplication.equals(password)) {
            return "Succes";
        }else{
            return "Failed";
        }
    	
    }

	@RequestMapping("/change/password")
	public String changePassword(@RequestParam(value="login", defaultValue="World") String login,
						@RequestParam(value="haslo", defaultValue="World") String haslo) {
		if( userServices.updatePassword(login,haslo)!=0) {
			return "Succes";
		}else{
			return "Failed";
		}
	}

	@RequestMapping("/change/about")
	public String changeAbout(@RequestParam(value="about", defaultValue="World") String aboutDescription,
						   @RequestParam(value="login", defaultValue="World") String login) {

		if(userServices.updateAbout(login,aboutDescription)!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}

	@RequestMapping("/deactivate")
	public String deactivate(@RequestParam(value="login", defaultValue="World") String login) {

		if(userServices.deactive(login)!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}

	@RequestMapping("/change/email")
	public String changeEmail(@RequestParam(value="email", defaultValue="World") String email,
							  @RequestParam(value="login", defaultValue="World") String login) {

		if(userServices.updateEmail(login,email)!=0) {
			return new String("Succes");
		}else{
			return new  String("Failed");
		}
	}

	@RequestMapping("/getGroupsForUser")
	public String getGroupForUser(@RequestParam(value="name") String name){
		return userServices.getGroupsByUser(name);
	}

	@RequestMapping("/getUsersForGroup")
	public String getUsersForGroup(@RequestParam(value="name") String name){
		return userServices.getUsersInGroup(name);
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

