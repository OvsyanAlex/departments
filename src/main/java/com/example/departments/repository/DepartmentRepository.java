package com.example.departments.repository;

import com.example.departments.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d FROM Department d WHERE d.id = ?1")
    Department findDepartmentById(Long id);

    @Query("select d FROM Department d WHERE d.name = ?1")
    Department findDepartmentByName(String name);
}
