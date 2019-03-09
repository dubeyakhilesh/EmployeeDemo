package com.example.employeedemo.leaveList;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.employeedemo.R;
import com.example.employeedemo.database.DatabaseHelper;
import com.example.employeedemo.database.model.EmployeeLeave;
import com.example.employeedemo.employeeList.adapter.EmployeeListAdapter;
import com.example.employeedemo.employeeList.model.Employee;
import com.example.employeedemo.leaveList.adapter.LeaveAdapter;
import com.example.employeedemo.utility.RecyclerTouchListener;

import java.util.ArrayList;

public class LeaveList extends AppCompatActivity {
    SwipeRefreshLayout srlList;
    RecyclerView rvList;
    LeaveAdapter leaveAdapter;
    ArrayList<EmployeeLeave> employeeLeaves;
    LinearLayoutManager linearLayoutManager;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_list);

        init();
    }

    private void init(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView txtTitle =  (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.leave_title));
        txtTitle.setVisibility(View.VISIBLE);
        databaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String name = intent.getStringExtra("name");

        srlList = (SwipeRefreshLayout)findViewById(R.id.srlList);
        srlList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLeaveList(id, name);
            }
        });
        rvList = (RecyclerView)findViewById(R.id.rvList);

        getLeaveList(id, name);
    }

    private void getLeaveList(String id, String name){
        employeeLeaves = new ArrayList<>();
        employeeLeaves = databaseHelper.fetchAllRecordWithId(id);
        leaveAdapter = new LeaveAdapter(this, employeeLeaves, name);
        linearLayoutManager = new LinearLayoutManager(this);
        rvList.setAdapter(leaveAdapter);
        rvList.setLayoutManager(linearLayoutManager);
    }
}
