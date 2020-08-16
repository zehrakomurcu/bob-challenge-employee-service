package com.takeaway.challenge.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "id"}))
public class Employee {

    @Id @GeneratedValue
    private UUID id;

    private String fullName;

    private String email;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date birthday;

    @ManyToOne
    private Department department;
}
