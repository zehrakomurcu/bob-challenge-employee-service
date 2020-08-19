/*
package com.takeaway.challenge;

import com.takeaway.challenge.controller.EmployeeController;
import com.takeaway.challenge.dto.EmployeeRequestBody;
import com.takeaway.challenge.model.Department;
import com.takeaway.challenge.model.Employee;
import com.takeaway.challenge.repository.DepartmentRepository;
import com.takeaway.challenge.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Test
    public void shouldCreateDepartment() throws Exception {
        //given
        Department department = new Department(1L, "Engineering");
        given(departmentRepository.save(department)).willReturn(department);

        mvc.perform(post("/employees/department")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(department.getName())));
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        String name = "Test User";
        String email = "test@email";
        String birthday = "1991-06-26";
        String department = "Engineering";
        EmployeeRequestBody request = new EmployeeRequestBody(name, email, birthday, department);
        given(employeeService.createEmployee(request)).willReturn(mockEmployeeEntity(UUID.randomUUID()));

        mvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Engineering")));
    }

    @Test
    public void shouldGetEmployee() throws Exception {
        UUID id = UUID.randomUUID();
        given(employeeService.getEmployee(id)).willReturn(mockEmployeeEntity(id));

        mvc.perform(get("/employees")
                .param(id.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Engineering")));

    }

    private Employee mockEmployeeEntity(UUID id) throws ParseException {
        String name = "Test User";
        String email = "test@email";
        String birthday = "1991-06-26";
        String department = "Engineering";

        return new Employee(id, name, email, dateFormat.parse(birthday), new Department(1L, "Egineering"));

    }

}

*/
