package com.asystent.Model;

import java.util.List;

/**
 * Created by Kamil on 2017-10-23.
 */
public class Games {

    private Long id;
    private String name;
    private List<Categories> categories;
    private boolean active;

    public Games() {}

    public Games(Long id, String name, List<Categories> categories, boolean active) {
        this.id = id;
        this.name = name;
        this.categories = categories;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
