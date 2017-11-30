package pwcdma.asystentgierplanszowych.model;

import java.util.List;

/**
 * Created by kriscool on 20.11.2017.
 */

public class Game {
    private Long id;
    private String name;
    private List<Categorie> categories;
    private boolean active;

    public Game() {}

    public Game(Long id, String name, List<Categorie> categories, boolean active) {
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

    public List<Categorie> getCategories() {
        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
