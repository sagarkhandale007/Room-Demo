package com.example.room_demo;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;

import com.example.room_demo.Database.DatabaseApp;
import com.example.room_demo.Database.AppExecutors;
import com.example.room_demo.Model.Employee;
import com.example.room_demo.utils.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    EditText name, phoneNumber, address;
    Button button;
    int mPersonId;
    Intent intent;
    private DatabaseApp mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = DatabaseApp.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Employee employee = mDb.empolyeeDao().loadPersonById(mPersonId);
                    populateUI(employee);
                }
            });


        }

    }


    private void populateUI(Employee employee) {

        if (employee == null) {
            return;
        }

        name.setText(employee.getName());
        phoneNumber.setText(employee.getNumber());
        address.setText(employee.getAddress());
    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        phoneNumber = findViewById(R.id.edit_number);
        address = findViewById(R.id.edit_Address);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
        final Employee employee = new Employee(
                name.getText().toString(),
                phoneNumber.getText().toString(),
                address.getText().toString());

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    mDb.empolyeeDao().insertPerson(employee);
                } else {
                    employee.setId(mPersonId);
                    mDb.empolyeeDao().updatePerson(employee);
                }
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}