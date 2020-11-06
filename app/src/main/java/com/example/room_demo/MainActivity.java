package com.example.room_demo;

import android.content.Intent;
import android.os.Bundle;


import com.example.room_demo.Adapter.EmployeeAdaptor;
import com.example.room_demo.Database.DatabaseApp;
import com.example.room_demo.Database.AppExecutors;
import com.example.room_demo.Model.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private EmployeeAdaptor mAdapter;
    private DatabaseApp mDb;
     LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, UpdateActivity.class));

            }
        });

        linearLayout = findViewById(R.id.dataNotFound);

        mRecyclerView = findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //------------------ Initialize the adapter and attach it to the RecyclerView------------------------------//
        mAdapter = new EmployeeAdaptor(this);
        mRecyclerView.setAdapter(mAdapter);
        mDb = DatabaseApp.getInstance(getApplicationContext());
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }

//-----------------------------------------  Data retrive from database and  set the adapter to binding-----------------------//
    public void retrieveTasks() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Employee> employees = mDb.empolyeeDao().loadAllPersons();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(employees.size() > 0){
                            mAdapter.setTasks(employees);
                            linearLayout.setVisibility(View.GONE);

                        }else{
                            mAdapter.setTasks(employees);
                            linearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                });



            }
        });

    }
}