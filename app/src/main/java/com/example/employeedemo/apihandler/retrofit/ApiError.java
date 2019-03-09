package com.example.employeedemo.apihandler.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by XT-DS-20 on 29 Dec 2018.
 * @CompanyName: Xipe Tech
 * @ProjectName: compete4Hope Android App
 * @Project Version: 1.0
 * @PageName: ApiError.java
 * @Module Name: Retrofit
 * @Description : show rest api error message
 * @Author: Akhilesh Dubey
 *
 */

public class ApiError {
    @SerializedName("message")
    @Expose
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage= errorMessage;
    }
}
