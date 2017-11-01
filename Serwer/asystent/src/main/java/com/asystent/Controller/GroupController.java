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
    public String getAllGroups(@RequestParam(value="name") String name) {
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
        return groupServices.allGroup();
    }

    @RequestMapping(value = "/user/group", method = RequestMethod.GET)
    public String getUserInGroup(@RequestParam(value="id") int id) {
        return groupServices.getUserInGroup(id);
    }

    @RequestMapping(value = "/group/add/user", method = RequestMethod.GET)
    public int addUserToGroup(@RequestParam(value="idGroup") int idGroup,@RequestParam(value="idUser") int idUser) {
        return groupServices.addUserToGroup(idUser,idGroup);
    }

    @RequestMapping(value = "/user/group/name", method = RequestMethod.GET)
    public String getUserInGroupByNameGroup(@RequestParam(value="name") String name) {
        return groupServices.getUserInGroupByNameGroup(name);
    }

    @RequestMapping(value = "/group/add", method = RequestMethod.GET)
    public int addGroup(@RequestParam(value="name") String name) {
        return groupServices.addGroup(name);
    }
}
