package com.example.MapperProject.Service;

import com.example.MapperProject.DTO.EmployeeDTO;
import com.example.MapperProject.Entity.Employee;
import com.example.MapperProject.Mapper.EmployeeMapper;
import com.example.MapperProject.Mapper.EmployeeModelMapper;
import com.example.MapperProject.Repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper mapper;
    private final EmployeeModelMapper mmMapper;


    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO){
        long start = System.nanoTime();
        Employee employee = mapper.toEntity(employeeDTO);
        logTime("createEmployee",start);
        start = System.nanoTime();
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO dto = mapper.toDTO(savedEmployee);
        logTime("savedEmployee",start);
        return dto;
    }

    public List<EmployeeDTO> getAllEmployees(){
        long start = System.nanoTime();
        List<Employee> employees = employeeRepository.findAll();
        logTime("getAllEmployees",start);
        start = System.nanoTime();
        List<EmployeeDTO> dto = mapper.toDTOList(employees);
        logTime("getAllEmployees",start);
        return dto;
    }

    public List<EmployeeDTO> createManyEmployees(List<EmployeeDTO> employeeDTOSList){
        long start = System.nanoTime();
        List<EmployeeDTO> saved = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOSList) {
            try {
                Employee entity = mapper.toEntity(dto);
                logTime("createManyEmployees",start);
                start = System.nanoTime();
                Employee row = employeeRepository.save(entity);
                saved.add(mapper.toDTO(row));
                logTime("savedManyEmployees",start);
            } catch (Exception e) {
                log.warn("Skipped employee {} {}: {}", dto.getFirstName(), dto.getLastName(), e.getMessage());
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

    public EmployeeDTO createEmployeeMM(EmployeeDTO employeeDTO){
        long start = System.nanoTime();
        Employee employee = mmMapper.toEntity(employeeDTO);
        logTime("createEmployeeMM",start);
        start = System.nanoTime();
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDTO dto = mapper.toDTO(savedEmployee);
        logTime("createEmployeeMM",start);
        return dto;
    }

    public List<EmployeeDTO> getAllEmployeesMM(){
        List<Employee> employees = employeeRepository.findAll();
        return mmMapper.toDTOList(employees);
    }

    public List<EmployeeDTO> createManyEmployeesMM(List<EmployeeDTO> employeeDTOSList){
        long start = System.nanoTime();
        List<EmployeeDTO> saved = new ArrayList<>();
        for (EmployeeDTO dto : employeeDTOSList) {
            try {
                Employee entity = mmMapper.toEntity(dto);
                logTime("createEmployeeMM",start);
                start = System.nanoTime();
                Employee row = employeeRepository.save(entity);
                saved.add(mmMapper.toDTO(row));
                logTime("savedEmployeeMM",start);
            } catch (Exception e) {
                log.warn("Skipped employee {} {}: {}", dto.getFirstName(), dto.getLastName(), e.getMessage());
            }
        }
        return saved;
    }

    public EmployeeDTO getByIdMM(Long id) {
        return employeeRepository.findById(id)
                .map(mmMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Employee not found with id " + id));
    }

    private void logTime(String label, long startNanos) {
        log.info("{} took {} ms", label, (System.nanoTime() - startNanos) / 1_000_000);
    }
}
