package com.example.employeedemo.employeeList.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeedemo.R;
import com.example.employeedemo.employeeList.model.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.EmployeeListItem> {
    /*-- define variable --*/
    Context context;
    ArrayList<Employee> employees;

    /*-- Constructor --*/
    public EmployeeListAdapter(Context context, ArrayList<Employee> employees){
        this.context = context;
        this.employees = employees;
    }

    @Override
    public EmployeeListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_item, parent, false);
        EmployeeListItem employeeListItem = new EmployeeListItem(view);
        return employeeListItem;
    }

    @Override
    public void onBindViewHolder(EmployeeListItem holder, int position) {
        Employee employee = employees.get(position);
        Picasso.with(context).load(employee.getImageUrl()).resize(120, 120).placeholder(R.drawable.image).error(R.drawable.image).into(holder.imgMain);
        holder.txtName.setText(employee.getName());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeListItem extends RecyclerView.ViewHolder{
        ImageView imgMain;
        TextView txtName;
        public EmployeeListItem(View itemView) {
            super(itemView);
            imgMain = (ImageView)itemView.findViewById(R.id.imgMain);
            txtName = (TextView)itemView.findViewById(R.id.txtName);
        }
    }
}
