package com.example.room_demo.Database;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.room_demo.Model.Employee;

import java.util.List;

@Dao
public interface EmpolyeeDao {

    @Query("SELECT * FROM Employee ORDER BY ID")
    List<Employee> loadAllPersons();

    @Insert
    void insertPerson(Employee employee);

    @Update
    void updatePerson(Employee employee);

    @Delete
    void delete(Employee employee);

    @Query("SELECT * FROM Employee WHERE id = :id")
    Employee loadPersonById(int id);
}
