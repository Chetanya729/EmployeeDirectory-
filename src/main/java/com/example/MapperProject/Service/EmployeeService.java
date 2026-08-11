package com.example.MapperProject.Service;

import com.example.MapperProject.DTO.EmployeeDTO;
import com.example.MapperProject.Entity.Employee;
import com.example.MapperProject.Mapper.EmployeeMapper;
import com.example.MapperProject.Repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
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

    public List<EmployeeDTO> createManyEmployees(List<EmployeeDTO> employeeDTOSList){
        List<EmployeeDTO> saved = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOSList) {
            try {
                Employee entity = mapper.toEntity(dto);
                Employee row = employeeRepository.save(entity);
                saved.add(mapper.toDTO(row));
            } catch (Exception e) {
                log.warn("Skipped employee {} {}: {}", dto.firstName(), dto.lastName(), e.getMessage());
            }
        }
        return saved;
    }
    public EmployeeDTO getById(Long id) {
        return employeeRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employee not found with id " + id));
    }

}
