package com.takeaway.challenge.controller;

import com.sun.java.swing.action.OkAction;
import com.takeaway.challenge.EmployeeNotFoundException;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/department")
    public ResponseEntity<Department> createDepartment(@RequestBody Department departmentRequest) {
        return ResponseEntity.ok(departmentRepository.save(departmentRequest));
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employeeRequest) {
        return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(employeeService.getEmployee(id));
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") UUID id, @RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OkAction> deleteEmployee(@PathVariable("id") UUID id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok().build();
        } catch (EmployeeNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
