package com.example.dataentry;

import java.time.LocalDate;

public class Person { // Person Class
    private String name;
    private String fatherName;
    private String cnic;
    private LocalDate dob;
    private String gender;
    private String city;

    public Person(String name, String fatherName, String cnic, LocalDate dob, String gender, String city) {
        this.name = name;
        this.fatherName = fatherName;
        this.cnic = cnic;
        this.dob = dob;
        this.gender = gender;
        this.city = city;
    }
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", cnic='" + cnic + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

