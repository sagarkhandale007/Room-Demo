package com.example.room_demo.Model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Employee")
public class Employee {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String number;
    String address;


    public Employee(){


    }
    @Ignore
    public Employee(String name, String number, String address) {
        this.name = name;
        this.number = number;
        this.address = address;
    }

    public Employee(int id, String name, String number,  String address) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.address = address;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
