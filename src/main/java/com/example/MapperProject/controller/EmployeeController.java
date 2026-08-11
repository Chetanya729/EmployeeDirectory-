package com.example.MapperProject.controller;


import com.example.MapperProject.DTO.EmployeeDTO;
import com.example.MapperProject.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployees());
    }
    @PostMapping("/create")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.createEmployee(employeeDTO));
    }
    @PostMapping("/createMany")
    public ResponseEntity<List<EmployeeDTO>> createManyEmployees(@RequestBody List<EmployeeDTO> employeeDTOList) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.createManyEmployees(employeeDTOList));
    }
    @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getById(id));
    }
}
