package com.example.MapperProject.Mapper;

import com.example.MapperProject.DTO.EmployeeDTO;
import com.example.MapperProject.Entity.Employee;
import com.example.MapperProject.enums.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

        // Entity to DTO
        @Mapping(source = "firstName",target = "firstName")
        @Mapping(source = "department",target = "department")
        EmployeeDTO toDTO(Employee employee);

        // DTO to Entity
        @Mapping(source = "firstName", target = "firstName")
        @Mapping(source = "department", target = "department")
        Employee toEntity(EmployeeDTO employeeDTO);

        List<EmployeeDTO> toDTOList(List<Employee> employees);

        default String mapDepartmenttoString(Department department){
             return department != null ? department.name() : null;
        }

        default Department mapStringtoDepartment(String value){
                return value != null ? Department.valueOf(value.toUpperCase()) : null;
        }

}
