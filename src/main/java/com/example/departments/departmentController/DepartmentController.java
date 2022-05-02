package com.example.departments.departmentController;

import com.example.departments.departmentDto.DepartmentDto;
import com.example.departments.departmentService.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    //    @Operation(summary = "Просмотр сведений о департаменте")
//    @GetMapping("/{id}")
//    public DepartmentDto getDepartmentById(@PathVariable Long id) {
//        return departmentService.getDepartment(id);
//    }
    @Operation(summary = "Поиск департамента по имени")
    @GetMapping("/{name}/search")
    public DepartmentDto getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }
}
