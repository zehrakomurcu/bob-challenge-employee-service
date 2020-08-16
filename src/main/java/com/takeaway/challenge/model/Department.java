package com.takeaway.challenge.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "departments")
public class Department {

    private @Id @GeneratedValue Long id;
    private String name;
}
