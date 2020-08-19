package com.takeaway.challenge.controller;

import com.takeaway.challenge.EmployeeNotFoundException;
import com.takeaway.challenge.dto.EmployeeRequestBody;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.service.EmployeeService;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @PostMapping("/department")
    public ResponseEntity createDepartment(@RequestBody Department departmentRequest) {
        try {
            return ResponseEntity.ok(departmentRepository.save(departmentRequest));
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Department exists with same name. Please try another one");
        }
    }

    @PostMapping
    public ResponseEntity createEmployee(@RequestBody EmployeeRequestBody employeeRequest) throws ParseException {
        try {
            return ResponseEntity.ok(employeeService.createEmployee(employeeRequest));
        } catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee exists with same email address. Please try another one");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") UUID id) throws EmployeeNotFoundException {
        return ResponseEntity.ok(employeeService.getEmployee(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") UUID id, @RequestBody EmployeeRequestBody employee) throws EmployeeNotFoundException, ParseException {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Any> deleteEmployee(@PathVariable("id") UUID id) throws EmployeeNotFoundException {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }


}
