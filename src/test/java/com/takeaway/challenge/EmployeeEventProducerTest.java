package com.takeaway.challenge;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.kafka.producer.EmployeeData;
import com.takeaway.challenge.kafka.producer.EmployeeEvent;
import com.takeaway.challenge.service.EmployeeProducerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.value;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka
public class EmployeeEventProducerTest {

    @Autowired
    private EmployeeProducerService service;

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String TOPIC = "changes.employee";

    private KafkaMessageListenerContainer listenerContainer;

    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafkaRule = new EmbeddedKafkaRule(1, true, TOPIC);


    @Before
    public void setup() {
        consumerRecords = new LinkedBlockingQueue<>();

        ContainerProperties containerProps = new ContainerProperties(TOPIC);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(
                "consumer",
                "false",
                embeddedKafkaRule.getEmbeddedKafka());

        DefaultKafkaConsumerFactory<String, EmployeeEvent> consumer = new DefaultKafkaConsumerFactory<>(consumerProps);

        listenerContainer = new KafkaMessageListenerContainer<>(consumer, containerProps);
        listenerContainer.setupMessageListener((MessageListener<String, String>) record -> {
            System.out.println("Listened message='{}'" + record.toString());
            consumerRecords.add(record);
        });
        listenerContainer.start();

        ContainerTestUtils.waitForAssignment(listenerContainer, embeddedKafkaRule.getEmbeddedKafka().getPartitionsPerTopic());
    }

    @After
    public void tearDown() {
        listenerContainer.stop();
    }

    @Test
    public void shouldSendEvent() throws JsonProcessingException, InterruptedException {
        EmployeeData data = new EmployeeData("test name", Date.from(Instant.now()), "test@email.com", "test department");
        EmployeeEvent event = new EmployeeEvent(EmployeeEvent.EventType.EMPLOYEE_CREATED, UUID.randomUUID().toString(), data);

        service.sendMessage(event);

        ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);
        String json = mapper.writeValueAsString(event);

        assertThat(received).isNotNull();
        assertThat(received).has(value(json));
    }

}
