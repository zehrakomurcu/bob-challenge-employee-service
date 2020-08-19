package com.takeaway.challenge.repository;

import com.takeaway.challenge.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, UUID> {
}
