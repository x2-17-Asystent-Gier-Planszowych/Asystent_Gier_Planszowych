package com.asystent.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by kriscool on 24.10.2017.
 */
@RestController
public class GroupController {

    @RequestMapping(value = "/categories/{catname}", method = RequestMethod.GET)
    public String getCategoryByName(@PathVariable("catname") String name) {
        return null;
    }
}
