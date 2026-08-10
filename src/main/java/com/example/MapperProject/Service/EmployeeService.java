package com.example.MapperProject.Service;

import com.example.MapperProject.DTO.EmployeeDTO;
import com.example.MapperProject.Entity.Employee;
import com.example.MapperProject.Mapper.EmployeeMapper;
import com.example.MapperProject.Repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;


    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        Employee employee = mapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return mapper.toDTO(savedEmployee);
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        return mapper.toDTOList(employees);
    }

    public EmployeeDTO getById(Long id) {
        return employeeRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employee not found with id " + id));
    }
}
