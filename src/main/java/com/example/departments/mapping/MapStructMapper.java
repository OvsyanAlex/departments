package com.example.departments.mapping;


import com.example.departments.departmentDto.DepartmentDto;
import com.example.departments.models.Department;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MapStructMapper {

    DepartmentDto toDepartmentDto(Department department);

    Department toDepartment(DepartmentDto departmentDto);
}
