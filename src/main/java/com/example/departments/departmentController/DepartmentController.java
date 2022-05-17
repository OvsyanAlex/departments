package com.example.departments.departmentController;

import com.example.departments.departmentDto.DepartmentDto;
import com.example.departments.departmentService.DepartmentService;
import com.example.departments.employeeApi.Employee;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Создание департамента")
    @PostMapping("/create")
    public DepartmentDto saveDepartment(@RequestBody DepartmentDto departmentDto) {
        return departmentService.addDepartment(departmentDto);
    }

    @Operation(summary = "Просмотр сведений о департаменте")
    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    @Operation(summary = "Поиск департамента по имени")
    @GetMapping("/{name}/search")
    public DepartmentDto getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }

    @Operation(summary = "Удаление департамента")
    @DeleteMapping("/{departmentName}/delete")
    public void deleteDepartment(@PathVariable String departmentName) {
        departmentService.deleteDepartment(departmentName);
    }

    @Operation(summary = "Просмотр сведений о сотрудниках департамента")
    @GetMapping("/{id}/employees")
    public List<Employee> getDepartmentEmployees(@PathVariable Long id) {
        return departmentService.getDepartmentEmployees(id);
    }

    @Operation(summary = "Зарплата сотрудников департамента")
    @GetMapping("/{id}/salary")
    public int getDepartmentSalaryDto(@PathVariable Long id) {
        return departmentService.getDepartmentSalaryDto(id);
    }

    @Operation(summary = "Изменение названия департамента")
    @PutMapping("{id}/{name}")
    public DepartmentDto changeDepartmentName(@PathVariable Long id, @PathVariable String name) {
        return departmentService.changeDepartmentName(id, name);
    }

    @Operation(summary = "Поиск департаментов находящихся в непосредственном подчинении")
    @GetMapping("{id}/search/children")
    public List<DepartmentDto> getChildDepartments(@PathVariable Long id) {
        return departmentService.getChildDepartments(id);
    }

    @Operation(summary = "Поиск департаментов находящихся в подчинении")
    @GetMapping("/{id}/search/all/children")
    public List<DepartmentDto> getAllChildDepartments(@PathVariable Long id) {
        return departmentService.getAllChildDepartments(id);
    }
    @Operation(summary = "Поиск вышестоящих департаментов")
    @GetMapping("/{id}/search/parent")
    public List<DepartmentDto> getParentDepartments(@PathVariable Long id) {
        return departmentService.getParentDepartments(id);
    }
}
