package com.example.departments.departmentService;


import com.example.departments.departmentDto.DepartmentDto;
import com.example.departments.employeeApi.Employee;
import com.example.departments.employeeApi.EmployeeController;
import com.example.departments.mapping.MapStructMapper;
import com.example.departments.models.Department;
import com.example.departments.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final MapStructMapper mapStructMapper;
    private final EmployeeController employeeController;

    public DepartmentService(DepartmentRepository departmentRepository, MapStructMapper mapStructMapper, EmployeeController employeeController) {
        this.departmentRepository = departmentRepository;
        this.mapStructMapper = mapStructMapper;
        this.employeeController = employeeController;
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

    public DepartmentDto getDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        return mapStructMapper.toDepartmentDto(department);
    }

    public void deleteDepartment(String departmentName) {
        // удаление департамента только если в нем нет сотрдуников

        if (employeeController.getDepartment(departmentName).isEmpty()) {
            List<Department> departmentList = departmentRepository.findAll();
            departmentRepository.deleteAll(departmentList);
        } else System.out.println("В департаменте есть сотрудники - он не может быть удален");
    }

    public List<Employee> getDepartmentEmployees(Long id) {
        Department department = departmentRepository.findById(id).orElse(null);
        return employeeController.getDepartment(department.getName());
    }

    public int getDepartmentSalaryDto(Long id) {
        // ищем департамент по ID и находим всех его сотрудников
        Department department = departmentRepository.findById(id).orElse(null);
        List<Employee> employeeList = employeeController.getDepartment(department.getName());

        // вычисляем зарплату всех сотрудников департамента
        int salary = 0;
        for (Employee employee : employeeList) {
            salary += employee.getSalary();
        }
        return salary;
    }

    public DepartmentDto changeDepartmentName(Long id, String name) {
        // ищем департамент по ID и все департаменты
        Department departmentForChange = departmentRepository.findById(id).orElse(null);
        List<Department> departments = departmentRepository.findAll();

        // проверяем уникальность нового имени
        boolean isDepartmentNamePresent = false;
        for (Department department : departments) {
            if (name.equals(department.getName())) {
                isDepartmentNamePresent = true;
                break;
            }
        }
        if (!isDepartmentNamePresent) {
            departmentForChange.setName(name);
        } else return null;

        Department departmentAfterChangedName = departmentRepository.save(departmentForChange);
        return mapStructMapper.toDepartmentDto(departmentAfterChangedName);
    }

    public List<DepartmentDto> getChildDepartments(Long id) {
        // ищем департамент по ID и все департаменты
        Department parentDepartment = departmentRepository.findById(id).orElse(null);
        List<Department> departments = departmentRepository.findAll();

        // ищем все департаменты в непосредственном подчинении
        List<Department> childDepartments = new ArrayList<>();
        for (Department department : departments) {
            if (department.getParentDepartment().equals(parentDepartment.getName())) {
                childDepartments.add(department);
            }
        }
        //полученные департаменты мапим в ДТО
        List<DepartmentDto> ChildDepartmentsDto = new ArrayList<>();
        for (Department department : childDepartments) {
            ChildDepartmentsDto.add(mapStructMapper.toDepartmentDto(department));
        }
        return ChildDepartmentsDto;
    }

    public List<DepartmentDto> getAllChildDepartments(Long id) {
        // ищем департамент по ID и все департаменты
        Department parentDepartment = departmentRepository.findById(id).orElse(null);
        List<Department> departments = departmentRepository.findAll();

        // ищем все подчиненные департаменты
        List<Department> allChildrenDepartments = new ArrayList<>();
        searchChildDepartments(departments, allChildrenDepartments, parentDepartment);

        //полученные департаменты мапим в ДТО
        List<DepartmentDto> allChildDepartmentsDto = new ArrayList<>();
        for (Department department : allChildrenDepartments) {
            allChildDepartmentsDto.add(mapStructMapper.toDepartmentDto(department));
        }
        return allChildDepartmentsDto;
    }

    // метод для поиска всех подчиненных департаментов
    public static void searchChildDepartments
    (List<Department> allDepartments, List<Department> childrenDepartments, Department parentDepartment) {
        for (Department department : allDepartments) {
            if (department.getParentDepartment().equals(parentDepartment.getName())) {
                childrenDepartments.add(department);
                searchChildDepartments(allDepartments, childrenDepartments, department);
            }
        }
    }

    public List<DepartmentDto> getParentDepartments(Long id) {
        // ищем департамент по ID и все департаменты
        Department department = departmentRepository.findById(id).orElse(null);
        List<Department> departments = departmentRepository.findAll();

        // ищем все вышестоящие департаменты
        List<Department> parentDepartments = new ArrayList<>();
        searchParentDepartments(departments, parentDepartments, department);

        //полученные департаменты мапим в ДТО
        List<DepartmentDto> departmentDtoList = new ArrayList<>();
        for (Department department1 : parentDepartments) {
            departmentDtoList.add(mapStructMapper.toDepartmentDto(department1));
        }
        return departmentDtoList;
    }

    // метод для поиска всех вышестоящих департаментов
    public static void searchParentDepartments
    (List<Department> allDepartments, List<Department> parentDepartments, Department childDepartment) {
        for (Department department : allDepartments) {
            if (department.getName().equals(childDepartment.getParentDepartment())) {
                parentDepartments.add(department);
                searchParentDepartments(allDepartments, parentDepartments, department);
            }
        }
    }
}
