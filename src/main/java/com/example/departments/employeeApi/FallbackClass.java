package com.example.departments.employeeApi;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FallbackClass implements ServiceFeignClient {
    @Override
    public List<Employee> getEmployees(String departmentName) {
        return null;
    }
}
