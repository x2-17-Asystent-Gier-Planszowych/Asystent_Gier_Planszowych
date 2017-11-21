package com.asystent.Controller;

import com.asystent.Service.GroupServices;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kriscool on 24.10.2017.
 */
@RestController
public class GroupController {

    @Autowired
    GroupServices groupServices;

    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String getAllGroups() {
        return groupServices.allGroup();
    }

    @RequestMapping(value = "/user/group", method = RequestMethod.GET)
    public String getUserInGroup(@RequestParam(value="id") int id) {
        return groupServices.getUserInGroup(id);
    }

    @RequestMapping(value = "/group/add/user", method = RequestMethod.GET)
    public String addUserToGroup(@RequestParam(value="idGroup") int idGroup,@RequestParam(value="idUser") int idUser) {
        if( groupServices.addUserToGroup(idUser,idGroup)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }
    @RequestMapping(value = "/group/add/user/name", method = RequestMethod.GET)
    public String addUserToGroupByName(@RequestParam(value="nameGroup") String nameGroup,@RequestParam(value="nameUser") String nameUser) {
        if( groupServices.addUserToGroupByName(nameUser,nameGroup)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }
    @RequestMapping(value = "/user/group/name", method = RequestMethod.GET)
    public String getUserInGroupByNameGroup(@RequestParam(value="name") String name) {
        return groupServices.getUserInGroupByNameGroup(name);
    }

    @RequestMapping(value = "/group/delete", method = RequestMethod.GET)
    public String deleteGroup(@RequestParam(value="name") String name) {
        if(groupServices.deleteGroup(name)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }


    @RequestMapping(value = "/group/delete/user", method = RequestMethod.GET)
    public String deleteUserFromGroup(@RequestParam(value="nameUser") String nameUser
    ,@RequestParam(value="nameGroup") String nameGroup) {
        if(groupServices.deleteUserFromGroup(nameUser,nameGroup)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public String addGroup(@RequestParam(value="name") String name, @RequestParam(value="owner") String owner) {
        if(groupServices.addGroup(name,owner)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }


    @RequestMapping(value = "/group/addGame", method = RequestMethod.GET)
    public String addGameToGroup(@RequestParam(value="nameGroup") String nameGroup, @RequestParam(value="nameGame") String nameGame) {
        if(groupServices.addGameToGroup(nameGroup,nameGame)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }

    @RequestMapping(value = "/group/setGame", method = RequestMethod.GET)
    public String setGameToGroup(@RequestParam(value="nameGroup") String nameGroup, @RequestParam(value="nameGame") String nameGame) {
        if(groupServices.setMainGame(nameGroup,nameGame)!=0) {
            return new String("Succes");
        }else{
            return new  String("Failed");
        }
    }

    @RequestMapping(value = "/group/getGame", method = RequestMethod.GET)
    public String getGameFromGroup(@RequestParam(value="nameGroup") String nameGroup) {
        return groupServices.getGamesFromGroup(nameGroup);
    }
}
      /*  String myJSONString = "{\"id\":1,\"groupName\":\"a\",\"active\":false}";
        JsonObject jobj = new Gson().fromJson(myJSONString, JsonObject.class);


        String result = jobj.get("groupName").toString();


        JsonElement je = new JsonParser().parse(groupServices.allGroup());
        JsonArray myArray = je.getAsJsonArray();
        String ret = null;
        for (JsonElement e : myArray)
        {
            JsonObject jo = e.getAsJsonObject();

            JsonPrimitive tsPrimitive = jo.getAsJsonPrimitive("groupName");
            ret = tsPrimitive.getAsString();
        }*/