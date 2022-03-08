package com.example.departments.departmentDto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DepartmentDto {
    @Schema(description = "Название департамента", example = "оцифровка")
    private String name;
    @Schema(description = "Дата создания департамента", example = "2018-06-05")
    private LocalDate dateOfCreation;
    @Schema(description = "Вышестоящий департамент", example = "шифрование")
    private String parentDepartment;
}
