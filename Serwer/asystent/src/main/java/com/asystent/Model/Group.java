package com.asystent.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kriscool on 01.11.2017.
 */
public class Group {
    @SerializedName("id")
    int id;
    @SerializedName("groupName")
    String groupName;

    public Group(){
        this(0,null,false);
    }
    public Group(int id,String groupName,Boolean actve){
        this.id=id;
        this.groupName=groupName;
        this.active = actve;
    }
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

    @SerializedName("active")
    Boolean active;


}
