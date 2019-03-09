package com.example.employeedemo.apihandler.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.acl.Group;

/**
 * Created by XT-DS-20 on 13 Dec 2018.
 * @CompanyName: Xipe Tech
 * @ProjectName: compete4Hope Android App
 * @Project Version: 1.0
 * @PageName: GroupResponse.java
 * @Module Name: Retrofit
 * @Description : base GroupResponse for networking
 * @Author: Akhilesh Dubey
 *
 */

public class GroupResponse implements Serializable {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("group")
    @Expose
    private Group group;
    private final static long serialVersionUID = 6051722433344616883L;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
