package com.example.departments.employeeApi;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeApi employeeApi;

    @GetMapping(
            "/employee/{departmentName}/searcher")
    public List<Employee> getDepartment(@PathVariable String departmentName) {
        return employeeApi.getEmployees(departmentName);
    }
}
