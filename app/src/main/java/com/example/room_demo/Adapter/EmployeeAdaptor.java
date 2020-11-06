package com.example.room_demo.Adapter;


import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room_demo.Database.AppExecutors;
import com.example.room_demo.Database.DatabaseApp;
import com.example.room_demo.MainActivity;
import com.example.room_demo.Model.Employee;
import com.example.room_demo.R;
import com.example.room_demo.UpdateActivity;
import com.example.room_demo.utils.Constants;

import java.util.List;

public class EmployeeAdaptor extends RecyclerView.Adapter<EmployeeAdaptor.MyViewHolder> {
    private Context context;
    private List<Employee> mEmployeeList;


    public EmployeeAdaptor(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdaptor.MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(mEmployeeList.get(i).getName());
        myViewHolder.number.setText(mEmployeeList.get(i).getNumber());
        myViewHolder.city.setText(mEmployeeList.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        if (mEmployeeList == null) {
            return 0;
        }
        return mEmployeeList.size();

    }

    public void setTasks(List<Employee> employeeList) {
        mEmployeeList = employeeList;
        notifyDataSetChanged();
    }

    public List<Employee> getTasks() {

        return mEmployeeList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, email, pincode, number, city;
        ImageView editImage,edit_delete;
        DatabaseApp mDb;

        MyViewHolder(@NonNull final View view) {
            super(view);
            mDb = DatabaseApp.getInstance(context);
            name = view.findViewById(R.id.emp_name);

            number = view.findViewById(R.id.emp_number);
            city = view.findViewById(R.id.emp_address);
            editImage = view.findViewById(R.id.edit_Image);
            edit_delete = view.findViewById(R.id.edit_delete);


            editImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int elementId = mEmployeeList.get(getAdapterPosition()).getId();
                    Intent i = new Intent(context, UpdateActivity.class);
                    i.putExtra(Constants.UPDATE_Person_Id, elementId);
                    context.startActivity(i);
                }
            });

            edit_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDb = DatabaseApp.getInstance(context);
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Employee employee =  mEmployeeList.get(getAdapterPosition());
                            mDb.empolyeeDao().delete(employee);
                            ((MainActivity) context).retrieveTasks();

                        }
                    });

                }
            });
        }




    }

}
