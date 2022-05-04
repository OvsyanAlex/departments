package com.example.departments.employeeApi;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Employee {
    private String surname;
    private String name;
    private String middleName;
    private String gender;
    private LocalDate birthDay;
    private String phone;
    private String email;
    private LocalDate dateOfEmployment;
    private LocalDate dateOfDismissal;
    private String post;
    private int salary;
    private boolean director;
    private String department;
}
