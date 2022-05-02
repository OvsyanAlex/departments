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

    public DepartmentDto getDepartmentByName(String name) {

        //ищем департамент по имени, мапим и возвращаем departmentDto
        Department department = departmentRepository.findDepartmentByName(name);
        return mapStructMapper.toDepartmentDto(department);
    }

    // переписать вызов findAll() на запрос по имени департамента и на запрос на наличие родительского департамента
    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        // ищем все департаменты
        List<Department> departments = departmentRepository.findAll();

        // проверяем уникальность департамента по имени и по наличию родительского департамента (возможно главный департамент уже есть)
        for (Department department : departments) {
            if (departmentDto.getName().equals(department.getName()) ||
                    departmentDto.getParentDepartment().equals(department.getParentDepartment())) {
                System.out.println("Недопустимые данные departmentDto");
                return null;
            }
        }
        //сохраняем Department
        Department department = mapStructMapper.toDepartment(departmentDto);
        Department departmentAfterAdd = departmentRepository.save(department);
        return mapStructMapper.toDepartmentDto(departmentAfterAdd);
    }
}
