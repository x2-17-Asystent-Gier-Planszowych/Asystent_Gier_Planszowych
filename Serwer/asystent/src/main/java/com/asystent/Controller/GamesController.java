package com.asystent.Controller;

import com.asystent.Service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Kamil on 2017-10-23.
 */
@RestController
public class GamesController {

    @Autowired
    GamesService gamesService;

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public List<String> getGames() {
        return gamesService.getAllGames();
    }
    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public String addGame(@RequestParam(value = "name") String name) {
        return gamesService.addGame(name);
    }
    @RequestMapping(value = "/games/addCat", method = RequestMethod.POST)
    public String addCategory(@RequestParam(value = "name") String gameName, @RequestParam(value = "category") String categoryName ) {
        return gamesService.addTag(gameName,categoryName);
    }

}
