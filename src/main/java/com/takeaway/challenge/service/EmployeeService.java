package com.takeaway.challenge.service;

import com.takeaway.challenge.EmployeeNotFoundException;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    public Employee createEmployee(Employee newEployee) {
        return repository.save(newEployee);
    }

    public Employee updateEmployee(UUID id, Employee requestedEmployee) {
        Optional<Employee> employee = repository.findById(id);

        if(employee.isPresent())
        {
            Employee newEntity = employee.get();
            newEntity.setFullName(requestedEmployee.getFullName());
            newEntity.setDepartment(requestedEmployee.getDepartment());
            newEntity.setBirthday(requestedEmployee.getBirthday());

            return repository.save(newEntity);

        } else {
            throw new EmployeeNotFoundException();
        }

    }

    public Employee getEmployee(UUID id) {
        Optional<Employee> employee = repository.findById(id);

        if(employee.isPresent()) {
            return employee.get();
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    public void deleteEmployee(UUID id) {
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException();
        }
    }
}
