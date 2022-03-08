package com.example.departments.departmentService;


import com.example.departments.departmentDto.DepartmentDto;
import com.example.departments.mapping.MapStructMapper;
import com.example.departments.models.Department;
import com.example.departments.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final MapStructMapper mapStructMapper;

    public DepartmentService(DepartmentRepository departmentRepository, MapStructMapper mapStructMapper) {
        this.departmentRepository = departmentRepository;
        this.mapStructMapper = mapStructMapper;
    }

    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        // ищем все департаменты
        List<Department> departments = departmentRepository.findAll();

        // ищем наличие главного департамента
        Department mainDepartment = null;
        for (Department department : departments) {
            if (department.getParentDepartment().equals(null)) {
                mainDepartment = department;
            }
        }

        // проверяем уникальность департамента по имени и по наличию родительского департамента (возможно главный департамент уже есть)
        for (Department department : departments) {
            if (department.getName().equals(departmentDto.getName()) ||
                    departmentDto.getParentDepartment().equals(mainDepartment.getParentDepartment())) {
                return null;
            }
        }

        Department department = mapStructMapper.toDepartment(departmentDto);
        Department departmentAfterAdd = departmentRepository.save(department);
        return mapStructMapper.toDepartmentDto(departmentAfterAdd);
    }
}
