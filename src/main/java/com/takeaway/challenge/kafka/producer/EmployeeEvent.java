package com.takeaway.challenge.kafka.producer;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class EmployeeEvent {
    private EventType eventType;
    private String sourceId;
    private EmployeeData data;

    public enum EventType {
        EMPLOYEE_CREATED, EMPLOYEE_UPDATED, EMPLOYEE_DELETED;
    }
}
