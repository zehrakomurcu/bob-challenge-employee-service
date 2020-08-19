package com.takeaway.challenge.kafka.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.kafka.producer.EmployeeEvent;
import org.apache.kafka.common.serialization.Serializer;

public class MessageSerializer implements Serializer<EmployeeEvent> {

    @Override
    public byte[] serialize(String topic, EmployeeEvent data) {
        byte[] serializedValue = null;
        ObjectMapper om = new ObjectMapper();
        if(data != null) {
            try {
                serializedValue = om.writeValueAsString(data).getBytes();
            } catch (JsonProcessingException e) {
            }
        }
        return serializedValue;
    }
}
