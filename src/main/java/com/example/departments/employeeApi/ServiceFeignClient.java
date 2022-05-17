package com.example.departments.employeeApi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "employeeApi", url = "http://localhost:8083", fallback = FallbackClass.class)
public interface ServiceFeignClient {
    @GetMapping( "employee/{departmentName}/searcher")
    List<Employee> getEmployees(@PathVariable String departmentName);
}
