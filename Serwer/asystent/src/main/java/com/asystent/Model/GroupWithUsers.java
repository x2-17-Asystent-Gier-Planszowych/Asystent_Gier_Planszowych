package com.asystent.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kamil on 2017-12-04.
 */
public class GroupWithUsers {
    @SerializedName("id")
    int id;
    @SerializedName("groupName")
    String groupName;
    @SerializedName("active")
    Boolean active;

    List<User> list;

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

    public List<User> getList() {
        return list;
    }

    public void setList(List<User> list) {
        this.list = list;
    }
}
