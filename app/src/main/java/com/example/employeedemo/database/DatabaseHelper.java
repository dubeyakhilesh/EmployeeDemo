package com.example.employeedemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.employeedemo.database.model.EmployeeLeave;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    /*--Database details--*/
    public static final String DATABASE_NAME = "employeeDb";
    public static final int DATABASE_VERSION = 1;

    /*--Other Variables--*/
    Context context;

    /*--Constructor--*/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /*--Default methods--*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EmployeeLeave.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(EmployeeLeave.DROP_TABLE);
        onCreate(db);
    }

    /*----------------------------Weather Details-----------------------------------------------*/
    public long insertRecord(EmployeeLeave employeeLeave) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EmployeeLeave.ID, employeeLeave.getId());
        contentValues.put(EmployeeLeave.START_DATE, employeeLeave.getStartDate());
        contentValues.put(EmployeeLeave.END_DATE, employeeLeave.getEndDate());
        contentValues.put(EmployeeLeave.DAYS, employeeLeave.getDays());
        contentValues.put(EmployeeLeave.STATUS, employeeLeave.getStatus());
        contentValues.put(EmployeeLeave.DESCRIPTION, employeeLeave.getDescription());
        long value = sqLiteDatabase.insert(EmployeeLeave.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        return value;
    }

    public ArrayList<EmployeeLeave> fetchAllRecord() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(EmployeeLeave.SELECT_ALL_RECORD, null);
        ArrayList<EmployeeLeave> employeeLeaves = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(EmployeeLeave.ID));
                String startDate = cursor.getString(cursor.getColumnIndex(EmployeeLeave.START_DATE));
                String endDate = cursor.getString(cursor.getColumnIndex(EmployeeLeave.END_DATE));
                String days = cursor.getString(cursor.getColumnIndex(EmployeeLeave.DAYS));
                String status = cursor.getString(cursor.getColumnIndex(EmployeeLeave.STATUS));
                String description = cursor.getString(cursor.getColumnIndex(EmployeeLeave.DESCRIPTION));
                EmployeeLeave employeeLeave = new EmployeeLeave(id, startDate, endDate, days, status, description);
                employeeLeaves.add(employeeLeave);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return employeeLeaves;
    }

    public ArrayList<EmployeeLeave> fetchAllRecordWithId(String ids) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String[] values = {ids};
        Cursor cursor = sqLiteDatabase.rawQuery(EmployeeLeave.SELECT_ALL_RECORD_ID_BASIS, values);
        ArrayList<EmployeeLeave> employeeLeaves = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(EmployeeLeave.ID));
                String startDate = cursor.getString(cursor.getColumnIndex(EmployeeLeave.START_DATE));
                String endDate = cursor.getString(cursor.getColumnIndex(EmployeeLeave.END_DATE));
                String days = cursor.getString(cursor.getColumnIndex(EmployeeLeave.DAYS));
                String status = cursor.getString(cursor.getColumnIndex(EmployeeLeave.STATUS));
                String description = cursor.getString(cursor.getColumnIndex(EmployeeLeave.DESCRIPTION));
                EmployeeLeave employeeLeave = new EmployeeLeave(id, startDate, endDate, days, status, description);
                employeeLeaves.add(employeeLeave);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return employeeLeaves;
    }

    public long deleteAllRecord() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        long value = sqLiteDatabase.delete(EmployeeLeave.TABLE_NAME, null, null);
        sqLiteDatabase.close();
        return value;
    }
}
