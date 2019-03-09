package com.example.employeedemo.leaveList.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.employeedemo.R;
import com.example.employeedemo.database.model.EmployeeLeave;
import com.example.employeedemo.employeeList.model.Employee;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.EmployeeListItem> {
    /*-- define variable --*/
    Context context;
    ArrayList<EmployeeLeave> employeeLeaves;
    String empName;

    /*-- Constructor --*/
    public LeaveAdapter(Context context, ArrayList<EmployeeLeave> employeeLeaves, String empName){
        this.context = context;
        this.employeeLeaves = employeeLeaves;
        this.empName = empName;
    }

    @Override
    public EmployeeListItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_item, parent, false);
        EmployeeListItem employeeListItem = new EmployeeListItem(view);
        return employeeListItem;
    }

    @Override
    public void onBindViewHolder(EmployeeListItem holder, int position) {
        EmployeeLeave employeeLeave = employeeLeaves.get(position);
        holder.txtEmpName.setText(context.getString(R.string.leave_empName) + " " + empName);
        holder.txtDays.setText(context.getString(R.string.leave_days) + " " + employeeLeave.getDays());
        holder.txtStartDate.setText(context.getString(R.string.leave_startDate) + " " + employeeLeave.getStartDate());
        holder.txtEndDate.setText(context.getString(R.string.leave_endDate) + " " + employeeLeave.getEndDate());
        holder.txtDescription.setText(context.getString(R.string.leave_description) + " " + employeeLeave.getDescription());
        holder.txtStatus.setText(context.getString(R.string.leave_status) + " " + employeeLeave.getStatus());
    }

    @Override
    public int getItemCount() {
        return employeeLeaves.size();
    }

    class EmployeeListItem extends RecyclerView.ViewHolder{
        TextView txtEmpName, txtStartDate, txtEndDate, txtDays, txtDescription, txtStatus;
        public EmployeeListItem(View itemView) {
            super(itemView);
            txtEmpName = (TextView)itemView.findViewById(R.id.txtEmpName);
            txtStartDate = (TextView)itemView.findViewById(R.id.txtStartDate);
            txtEndDate = (TextView)itemView.findViewById(R.id.txtEndDate);
            txtDays = (TextView)itemView.findViewById(R.id.txtDays);
            txtDescription = (TextView)itemView.findViewById(R.id.txtDescription);
            txtStatus = (TextView)itemView.findViewById(R.id.txtStatus);
        }
    }
}
