package com.asystent.Controller;

import com.asystent.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * Created by Kamil on 2017-10-18.
 */
@RestController
public class CategoriesController {

    @Autowired
    CategoriesService categoriesService;


    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<String> getCategories() {
        return categoriesService.getCats();
    }

    @RequestMapping(value = "/categories/{catname}", method = RequestMethod.GET)
    public String getCategoryByName(@PathVariable("catname") String name) {
        return categoriesService.getCatByName(name);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.POST)
    public String addCategory(@RequestParam(value = "name") String name) {
        return categoriesService.addCat(name);
    }

    @RequestMapping(value = "categories/{catname}/inactive", method = RequestMethod.GET)
    public String deleteCategory(@PathVariable(value = "catname") String name){
        return categoriesService.changeActive(name);
    }


}
