package com.takeaway.challenge;

import com.takeaway.challenge.dto.EmployeeRequestBody;
import com.takeaway.challenge.kafka.producer.EmployeeData;
import com.takeaway.challenge.kafka.producer.EmployeeEvent;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.repository.EmployeeRepository;
import com.takeaway.challenge.service.EmployeeProducerService;
import com.takeaway.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    DepartmentRepository departmentRepository;

    @MockBean
    EmployeeProducerService employeeProducerService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Before
    public void setUp() {


        //when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        //departmentRepository.findDepartmentByName(departmentName);

        //employeeProducerService.sendMessage(event)
    }

    @Test
    public void shouldCreateEmployee() throws ParseException {
        //given
        Employee employee = mockEmployeeEntity();

        Optional<String> name = Optional.of(employee.getFullName());
        Optional<String> email = Optional.of(employee.getEmail());
        Optional<String> birthday = Optional.of(dateFormat.format(employee.getBirthday()));
        Optional<String> departmentName = Optional.of(employee.getDepartment().getName());
        EmployeeRequestBody request = new EmployeeRequestBody(name, email, birthday, departmentName);

        //when
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(departmentRepository.save(employee.getDepartment())).thenReturn(employee.getDepartment());

        Employee found = employeeService.createEmployee(request);

        //then
        assertThat(found).isNotNull();
    }

    @Test
    public void shouldGetEmployee() {}

    @Test
    public void shouldUpdateEmployee() {}

    @Test
    public void shouldDeleteEmployee() {}


    private Employee mockEmployeeEntity() {
        UUID employeeId = UUID.randomUUID();
        String name = "Test Name";
        String email = "test@email.com";
        Date birthday = Date.from(Instant.now());
        Department department = new Department("Test Department");
        return new Employee(employeeId, name , email , birthday, department);
    }

    private EmployeeEvent mockEmployeeEvent(Employee entity, EmployeeEvent.EventType eventType) {
        EmployeeData data = new EmployeeData(
                entity.getFullName(),
                entity.getBirthday(),
                entity.getEmail(),
                entity.getDepartment().getName());
        return new EmployeeEvent(eventType, entity.getId().toString(), data);
    }
}
