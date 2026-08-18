package com.example.MapperProject.controller;


import com.example.MapperProject.DTO.EmployeeDTO;
import com.example.MapperProject.Entity.Employee;
import com.example.MapperProject.Service.EmployeeService;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EntityManagerFactory emf;

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
    @GetMapping("/name")
    public ResponseEntity<List<Employee>>findAllbyName() {
        return ResponseEntity.ok().body(employeeService.findEmployees("HR"));
    }

    @GetMapping("/mm")
    public ResponseEntity<List<EmployeeDTO>> getAllMM() {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getAllEmployeesMM());
    }
    @PostMapping("/mm/create")
    public ResponseEntity<EmployeeDTO> createEmployeeMM(@RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.createEmployeeMM(employeeDTO));
    }
    @PostMapping("/mm/createMany")
    public ResponseEntity<List<EmployeeDTO>> createManyEmployeesMM(@RequestBody List<EmployeeDTO> employeeDTOList) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.createManyEmployeesMM(employeeDTOList));
    }
    @GetMapping("/mm/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeMM(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.getByIdMM(id));
    }
    @PostMapping("/page")
    public ResponseEntity<Page<Employee>> getEmployeePage(@RequestBody Employee employee, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value ="size",defaultValue = "10") int size) {
        return ResponseEntity.ok().body(employeeService.EmployeePage(employee, page, size));
    }
    @GetMapping("/stats")
    public ResponseEntity<Map<String,Object>> getStats() {
        Statistics s = emf.unwrap(SessionFactory.class).getStatistics();
        return ResponseEntity.ok(Map.of(
                "l2Hits",           s.getSecondLevelCacheHitCount(),
                "l2Misses",         s.getSecondLevelCacheMissCount(),
                "l2Puts",           s.getSecondLevelCachePutCount(),
                "queryCacheHits",   s.getQueryCacheHitCount(),
                "queryCacheMisses", s.getQueryCacheMissCount(),
                "queriesExecuted",  s.getQueryExecutionCount(),
                "entitiesLoaded",   s.getEntityLoadCount()
        ));
    }

    @PostMapping("/stats/reset")
    public ResponseEntity<String> resetStats() {
        emf.unwrap(SessionFactory.class).getStatistics().clear();
        return ResponseEntity.ok("cleared");
    }
}
