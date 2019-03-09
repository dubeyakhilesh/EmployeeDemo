package com.example.employeedemo.database.model;

public class EmployeeLeave {
    /*--Variables--*/
    String id, startDate, endDate, days, status, description;

    /*--Tables, Columns and Query--*/
    public static final String TABLE_NAME = "leave";
    public static final String ID = "id";
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String DAYS = "days";
    public static final String STATUS = "status";
    public static final String DESCRIPTION = "description";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " TEXT, " + START_DATE + " TEXT, "
            + END_DATE + " TEXT, " + DAYS + " TEXT, " + STATUS + " TEXT, " + DESCRIPTION + " TEXT)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL_RECORD = "SELECT * FROM " + TABLE_NAME;
    public static final String SELECT_ALL_RECORD_ID_BASIS = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID + " =?";

    public EmployeeLeave(String id, String startDate, String endDate, String days, String status, String description){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.status = status;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDays() {
        return days;
    }

    public String getStatus() {
        return status;
    }
}
