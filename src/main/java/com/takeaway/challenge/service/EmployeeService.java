package com.takeaway.challenge.service;

import com.takeaway.challenge.EmployeeNotFoundException;
import com.takeaway.challenge.dto.EmployeeRequestBody;
import com.takeaway.challenge.kafka.producer.EmployeeData;
import com.takeaway.challenge.kafka.producer.EmployeeEvent;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository repository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeProducerService employeeProducerService;

    @Transactional
    public Employee createEmployee(EmployeeRequestBody employeeRequest) throws ParseException {
        Employee entity = convertDtoToEntity(employeeRequest);

        repository.save(entity);
        sendEmployeeEvent(EmployeeEvent.EventType.EMPLOYEE_CREATED, entity);

        return entity;
    }

    public Employee updateEmployee(UUID id, EmployeeRequestBody request) throws ParseException {
        Optional<Employee> employee = repository.findById(id);

        if(employee.isPresent())
        {
            Employee newEntity = employee.get();
            if(request.getName().isPresent()) { newEntity.setFullName(request.getName().get()); }
            if(request.getEmail().isPresent()) { newEntity.setFullName(request.getEmail().get()); }
            if(request.getDepartment().isPresent()) { newEntity.setDepartment(setDepartmentConverted(request.getDepartment().get())); }
            if(request.getBirthday().isPresent()) { newEntity.setBirthday(request.getBirthdayDateConverted()); }

            Employee updatedEntity = repository.save(newEntity);

            sendEmployeeEvent(EmployeeEvent.EventType.EMPLOYEE_UPDATED, updatedEntity);

            return updatedEntity;
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

            sendEmployeeEvent(EmployeeEvent.EventType.EMPLOYEE_DELETED, employee.get());
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    private Employee convertDtoToEntity(EmployeeRequestBody request) throws ParseException {
        Employee employee = new Employee();
        employee.setFullName(request.getName().get());
        employee.setEmail(request.getEmail().get());
        employee.setBirthday(request.getBirthdayDateConverted());
        employee.setDepartment(setDepartmentConverted(request.getDepartment().get()));

        return employee;
    }

    private Department setDepartmentConverted(String departmentName) {
        Optional<Department> optionalDepartment = departmentRepository.findDepartmentByName(departmentName);

        if(optionalDepartment.isPresent()) {
            return optionalDepartment.get();
        } else {
            Department department = new Department(departmentName);
            return departmentRepository.save(department);
        }
    }

    private void sendEmployeeEvent(EmployeeEvent.EventType eventType, Employee entity) {
        EmployeeData data = new EmployeeData(
                entity.getFullName(),
                entity.getBirthday(),
                entity.getEmail(),
                entity.getDepartment().getName());
        EmployeeEvent event = new EmployeeEvent(eventType, entity.getId().toString(), data);

        employeeProducerService.sendMessage(event);
    }
}
