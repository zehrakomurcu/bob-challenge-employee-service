package com.takeaway.challenge.service;

import com.takeaway.challenge.kafka.producer.EmployeeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmployeeProducerService {

    static final String TOPIC = "changes.employee";

    @Autowired
    private KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    public void sendMessage(EmployeeEvent msg) {
        kafkaTemplate.send(TOPIC, msg);
    }
}
