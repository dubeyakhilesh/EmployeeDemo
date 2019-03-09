package com.example.employeedemo.employeeList;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.employeedemo.R;
import com.example.employeedemo.apihandler.retrofit.ApiClient;
import com.example.employeedemo.apihandler.retrofit.ApiInterface;
import com.example.employeedemo.employeeDetails.EmployeeDetails;
import com.example.employeedemo.employeeList.adapter.EmployeeListAdapter;
import com.example.employeedemo.employeeList.model.Employee;
import com.example.employeedemo.employeeList.model.EmployeeDataResponse;
import com.example.employeedemo.utility.AppManager;
import com.example.employeedemo.utility.RecyclerTouchListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    SwipeRefreshLayout srlList;
    RecyclerView rvList;
    EmployeeListAdapter employeeListAdapter;
    ArrayList<Employee> employees;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView txtTitle =  (TextView)findViewById(R.id.txtTitle);
        txtTitle.setText(getString(R.string.employeeList_title));
        txtTitle.setVisibility(View.VISIBLE);

        progressDialog = AppManager.initProgress(this);
        srlList = (SwipeRefreshLayout)findViewById(R.id.srlList);
        srlList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getList();
            }
        });
        rvList = (RecyclerView)findViewById(R.id.rvList);
        rvList.addOnItemTouchListener(new RecyclerTouchListener(this, rvList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                moveToDetail(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        employees = new ArrayList<>();
        employeeListAdapter = new EmployeeListAdapter(this, employees);
        linearLayoutManager = new LinearLayoutManager(this);
        rvList.setAdapter(employeeListAdapter);
        rvList.setLayoutManager(linearLayoutManager);

        getList();

    }

    private void moveToDetail(int position){
        Employee employee = employees.get(position);
        Gson gson = new Gson();
        String emp = gson.toJson(employee);
        Intent intent = new Intent(this, EmployeeDetails.class);
        intent.putExtra("emp", emp);
        intent.putExtra("pos", position);
        startActivity(intent);
    }

    private void getList(){
        if(!AppManager.isConnectingToInternet(this)) {
            AppManager.showToast(this,  getString(R.string.error_internet));
            return;
        }

        ApiInterface apiInterface = ApiClient.getUserClient().create(ApiInterface.class);
        Call<EmployeeDataResponse> call = null;

        try{
            call = apiInterface.employeeData();
        }catch (Exception ex){
            AppManager.showToast(this,  getString(R.string.error_serverError));
        }

        if(call != null){
            if(progressDialog != null && !progressDialog.isShowing())
                progressDialog.show();

            call.enqueue(new Callback<EmployeeDataResponse>() {
                @Override
                public void onResponse(Call<EmployeeDataResponse> call, Response<EmployeeDataResponse> response) {
                    if(progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    int status = response.code();
                    if (status == 200) {
                        ArrayList<Employee> employee = response.body().getEmployees();
                        employees.addAll(employee);
                        employeeListAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeDataResponse> call, Throwable t) {
                    if(progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            });
        }
    }
}
