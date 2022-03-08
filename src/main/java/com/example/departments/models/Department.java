package com.example.departments.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Entity
@Table(name = "departments")
@Accessors(chain = true)
public class Department {
    @Id
    @Setter
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Getter
    @Column(name = "name")
    @Pattern(regexp = "[А-Яа-я-]")
    private String name;

    @Setter
    @Getter
    @Column(name = "date_of_creation")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfCreation;

    @Setter
    @Getter
    @Column(name = "parent_department")
    private String parentDepartment;
}
