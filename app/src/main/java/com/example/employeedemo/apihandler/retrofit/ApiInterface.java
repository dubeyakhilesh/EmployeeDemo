package com.example.employeedemo.apihandler.retrofit;

import com.example.employeedemo.employeeList.model.EmployeeDataResponse;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Akhilesh MAC on 8 March 2019.
 * @ProjectName: EmployeeDemo Android App
 * @Project Version: 1.0
 * @PageName: ApiInterface.java
 * @Module Name: Retrofit
 * @Description : base ApiInterface for networking
 * @Author: Akhilesh Dubey
 *
 */

public interface ApiInterface {

    /*--for login--*/
    @GET("heroes.php")
    Call<EmployeeDataResponse> employeeData();

}


