package com.example.employeedemo.employeeDetails.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeedemo.R;
import com.example.employeedemo.database.DatabaseHelper;
import com.example.employeedemo.database.model.EmployeeLeave;
import com.example.employeedemo.utility.AppManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ApplyLeave extends Dialog implements DatePickerDialog.OnDateSetListener{

    Context context;
    Activity activity;
    ImageView imgClose;
    FrameLayout flStartDate, flEndDate;
    Button btnApply;
    TextView txtStartDate, txtEndDate, txtDays;
    EditText edtDescription;
    int typeOfDate = 0;
    DatabaseHelper databaseHelper;
    String id;

    public ApplyLeave(@NonNull Context context, String id) {
        super(context);
        this.context = context;
        this.id = id;
        activity = (Activity)context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_leave);
        init();
    }

    private void init(){
        databaseHelper = new DatabaseHelper(context);

        imgClose = (ImageView) findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        typeOfDate = 0;
        flStartDate = (FrameLayout)findViewById(R.id.flStartDate);
        flStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfDate = 0;
                openDatePickerStartdate();
            }
        });

        flEndDate = (FrameLayout)findViewById(R.id.flEndDate);
        flEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeOfDate = 1;
                openDatePickerEndDate();
            }
        });

        btnApply = (Button) findViewById(R.id.btnApply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyLeave();
            }
        });

        txtStartDate = (TextView)findViewById(R.id.txtStartDate);
        txtEndDate = (TextView)findViewById(R.id.txtEndDate);
        txtDays = (TextView)findViewById(R.id.txtDays);
        edtDescription = (EditText)findViewById(R.id.edtDescription);
    }

    /*-- Open Date Picker--*/
    public void openDatePickerStartdate(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), DatePickerDialog.THEME_HOLO_LIGHT,this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void openDatePickerEndDate(){
        String startDate = txtStartDate.getText().toString().trim();
        if(AppManager.isValidString(startDate)){
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            formatter.setTimeZone(TimeZone.getDefault());

            try {
                Date endDate = formatter.parse(startDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), DatePickerDialog.THEME_HOLO_LIGHT,this, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            } catch (ParseException e) {
                e.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            AppManager.showToast(activity, context.getString(R.string.employeeDetail_startDate));
        }
    }

    private void getDays(){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String inputString1 = txtStartDate.getText().toString().trim();
        String inputString2 = txtEndDate.getText().toString().trim();

        try {
            Date date1 = formatter.parse(inputString1);
            Date date2 = formatter.parse(inputString2);
            long diff = date2.getTime() - date1.getTime();
            txtDays.setText("" + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            //System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void applyLeave(){
        String startDate, endDate, days, status = "Pending", description;
        startDate = txtStartDate.getText().toString().trim();
        endDate = txtEndDate.getText().toString().trim();
        days = txtDays.getText().toString().trim();
        description = edtDescription.getText().toString().trim();
        if(!AppManager.isValidString(startDate)){
            AppManager.showToast(activity, context.getString(R.string.employeeDetail_startDate));
            return;
        }

        if(!AppManager.isValidString(endDate)){
            AppManager.showToast(activity, context.getString(R.string.employeeDetail_endDate));
            return;
        }

        if(!AppManager.isValidString(description)){
            AppManager.showToast(activity, context.getString(R.string.employeeDetail_descriptionMissing));
            return;
        }

        EmployeeLeave employeeLeave = new EmployeeLeave(id, startDate, endDate, days, status, description);
        long value = databaseHelper.insertRecord(employeeLeave);
        if(value > 0){
            AppManager.showToast(activity, context.getString(R.string.employeeDetail_leaveSuccess));
            dismiss();
        }else{
            AppManager.showToast(activity, context.getString(R.string.employeeDetail_leaveUnsuccess));
        }
    }

    @Override
    public void onDateSet(DatePicker view, int i, int i1, int i2) {
        String d = "";
        if(i2 < 10) {
            d = "0" + i2;
            //d = "" + selectedDay;
        }else {
            d = "" + i2;
        }
        String m = "";
        if(i1 < 9) {
            m = "0" + (i1 + 1);
            //m = "" + (selectedMonth + 1);
        }else {
            m = "" + (i1 + 1);
        }
        String selectedDate = "" + m + "/" + d + "/" + i;
        if(typeOfDate == 1) {
            txtEndDate.setText(selectedDate);
            getDays();
        }else {
            //txtEndDate.setText("");
            txtStartDate.setText(selectedDate);
        }
    }
}
