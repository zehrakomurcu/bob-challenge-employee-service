package com.takeaway.challenge.repository;

import com.takeaway.challenge.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    Optional<Department> findDepartmentByName(String name);
}
