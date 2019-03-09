package com.example.employeedemo.employeeDetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeedemo.R;
import com.example.employeedemo.employeeDetails.dialog.ApplyLeave;
import com.example.employeedemo.employeeList.model.Employee;
import com.example.employeedemo.leaveList.LeaveList;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class EmployeeDetails extends AppCompatActivity {
    int position;
    Employee employee;
    Button btnApplyLeave, btnViewLeave;

    TextView txtEmpName, txtEmpDesignation, txtAge;
    ImageView imgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        init();
    }

    private void init(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView txtTitle =  (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.employeeDetail_title));
        txtTitle.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        String emp = intent.getStringExtra("emp");
        position = intent.getIntExtra("pos", 0);
        Gson gson = new Gson();
        employee = gson.fromJson(emp, Employee.class);

        txtEmpName = (TextView)findViewById(R.id.txtEmpName);
        txtEmpName.setText(getString(R.string.leave_empName) + " " +employee.getName());
        txtAge = (TextView)findViewById(R.id.txtEmpAge);
        txtAge.setText(getString(R.string.leave_empName) + " 30");
        txtEmpDesignation = (TextView)findViewById(R.id.txtEmpDesignation);
        txtEmpDesignation.setText(getString(R.string.leave_designation) + " Software Engineer");

        imgMain = (ImageView)findViewById(R.id.imgMain);
        Picasso.with(this).load(employee.getImageUrl()).placeholder(R.drawable.image).error(R.drawable.image).into(imgMain);



        btnApplyLeave = (Button)findViewById(R.id.btnApplyLeave);
        btnApplyLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyLeave applyLeave = new ApplyLeave(EmployeeDetails.this, "emp" + position);
                applyLeave.show();
            }
        });

        btnViewLeave = (Button)findViewById(R.id.btnViewLeave);
        btnViewLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EmployeeDetails.this, LeaveList.class);
                intent1.putExtra("id", "emp" + position);
                intent1.putExtra("name", employee.getName());
                startActivity(intent1);
            }
        });
    }
}
