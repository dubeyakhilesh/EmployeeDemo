package com.example.employeedemo.employeeList.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmployeeDataResponse {
    @SerializedName("heroes")
    @Expose
    private ArrayList<Employee> employees;

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }
}
