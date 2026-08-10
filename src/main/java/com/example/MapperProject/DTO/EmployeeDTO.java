package com.example.MapperProject.DTO;

import com.example.MapperProject.enums.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record EmployeeDTO(   Long id,
        String firstName,
        String lastName,
        String department) {
}
