package com.asystent.Controller;

import com.asystent.Service.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Kamil on 2017-10-23.
 */
@RestController
public class GamesController {

    @Autowired
    GamesService gamesService;

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public String getGames() {
        return gamesService.getAllGames();
    }

    @RequestMapping(value = "/games/addGame", method = RequestMethod.GET)
    public String addGame(@RequestParam(value = "name") String name) {
        return gamesService.addGame(name);
    }

    @RequestMapping(value = "/games/addCat", method = RequestMethod.GET)
    public String addCategoryToGame(@RequestParam(value = "name") String gameName, @RequestParam(value = "category") String categoryName ) {
        return gamesService.addTag(gameName,categoryName);
    }

    @RequestMapping(value = "/games/getTags", method = RequestMethod.GET)
    public String getTags(@RequestParam(value = "name") String name){
        return gamesService.getGameTag(name);
    }

    @RequestMapping(value = "/games/{gameName}", method = RequestMethod.GET)
    public String getGameByName(@PathVariable(value = "gameName") String name){
        return gamesService.getGameByName(name);
    }

    @RequestMapping(value = "/games/{gameName}/inactive")
    public String deleteGame(@PathVariable(value = "gameName") String name){
        return gamesService.deleteGame(name);
    }

    @RequestMapping(value = "/games/getByTags", method = RequestMethod.GET)
    public String getByTags(@RequestParam(value = "tag1") String tag1,
                            @RequestParam(value = "tag2", defaultValue = "") String tag2,
                            @RequestParam(value = "tag3", defaultValue = "") String tag3){
        return gamesService.getByTags(tag1,tag2,tag3);
    }

    @RequestMapping(value = "/games/addGame2", method = RequestMethod.GET)
    public String getByTags(@RequestParam(value = "name") String name,@RequestParam(value = "tag") String tag){
        return gamesService.addGameWithTag(name,tag);
    }
   //

}
