package com.takeaway.challenge.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeData {
    private String name;
    private Date birthday;
    private String email;
    private String departmentName;
}
