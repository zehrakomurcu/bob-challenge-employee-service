package com.takeaway.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takeaway.challenge.dto.EmployeeRequestBody;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    public void shouldCreateDepartment() throws Exception {
        //given
        String departmentName = "Test Department";
        Department department = new Department(departmentName);
        Department savedEntity = new Department(1L, departmentName);

        //when
        when(departmentRepository.save(department)).thenReturn(savedEntity);

        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/employees/department")
                .content(asJsonString(department))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        //given
        Optional<String> name = Optional.of("Test Name");
        Optional<String> email = Optional.of("test@email.com");
        Optional<String> birthday = Optional.of("2020-08-23");
        Optional<String> departmentName = Optional.of("Test Department");
        EmployeeRequestBody request = new EmployeeRequestBody(name, email, birthday,departmentName);

        Department department = new Department(1L, departmentName.get());
        Employee employee = new Employee(UUID.randomUUID(), name.get(), email.get(), request.getBirthdayDateConverted(), department);

        //when
        when(employeeService.createEmployee(request)).thenReturn(employee);

        //then
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/employees")
                .content("{\"name\":\"Test Name\", \"email\":\"test@email.com\", \"birthday\":\"2020-08-23\", \"department\":\"Test Department\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void shouldGetEmployeeById() throws Exception {
        //given
        UUID employeeId = UUID.randomUUID();
        String name = "Test Name";
        String email = "test@email.com";
        Date birthday = Date.from(Instant.now());
        Department department = new Department(1L, "Test Department");
        Employee employee = new Employee(employeeId, name , email , birthday, department);

        //when
        when(employeeService.getEmployee(employeeId)).thenReturn(employee);

        //then
        this.mockMvc.perform( MockMvcRequestBuilders
                .get("/employees/{id}", employeeId)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employeeId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(email))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(department));
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {
        //given
        UUID employeeId = UUID.randomUUID();
        Optional<String> name = Optional.of("Test Name");
        Optional<String> email = Optional.of("test@email.com");
        Optional<String> birthday = Optional.of("2020-08-23");
        Optional<String> departmentName = Optional.of("Test Department");
        EmployeeRequestBody request = new EmployeeRequestBody(name, email, birthday,departmentName);

        Department department = new Department(1L, departmentName.get());
        Employee updatedEmployee = new Employee(employeeId, name.get(), email.get(), request.getBirthdayDateConverted(), department);

        //when
        when(employeeService.updateEmployee(employeeId, request)).thenReturn(updatedEmployee);

        //then
        this.mockMvc.perform( MockMvcRequestBuilders
                .put("/employees/{id}", employeeId.toString())
                .content("{\"name\":\"Test Name\", \"email\":\"test@email.com\", \"birthday\":\"2020-08-23\", \"department\":\"Test Department\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employeeId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").value(updatedEmployee.getFullName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedEmployee.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.department").value(updatedEmployee.getDepartment()));;
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {
        UUID employeeId = UUID.randomUUID();
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/employees/{id}", employeeId) )
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
