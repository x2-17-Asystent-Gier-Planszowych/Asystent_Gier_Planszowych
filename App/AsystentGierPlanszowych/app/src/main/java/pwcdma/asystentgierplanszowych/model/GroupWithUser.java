package pwcdma.asystentgierplanszowych.model;

import java.util.List;

/**
 * Created by kriscool on 04.12.2017.
 */

public class GroupWithUser {
    int id;
    String groupName;
    Boolean active;

    String about;

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    List<Test> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Test> getList() {
        return list;
    }

    public void setList(List<Test> list) {
        this.list = list;
    }
}
