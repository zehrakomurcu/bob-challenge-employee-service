package com.takeaway.challenge.controller;

import com.sun.java.swing.action.OkAction;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.request.CreateDepartmentRequest;
import com.takeaway.challenge.request.CreateEmployeeRequest;
import com.takeaway.challenge.service.EmployeeService;
import org.omg.CORBA.Any;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Employee> getEmployee(UUID id) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Employee> updateEmployee(UUID id) {
        return ResponseEntity.ok().build();

    }

    @DeleteMapping
    public ResponseEntity<OkAction> deleteEmployee(UUID id){
     return ResponseEntity.ok().build();
    }


}
