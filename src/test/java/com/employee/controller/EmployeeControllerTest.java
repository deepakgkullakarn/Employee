package com.employee.controller;

import com.employee.model.Employee;
import com.employee.service.EmployeeHelperService;
import com.employee.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;


import static com.employee.constants.EmployeeConst.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private EmployeeHelperService excelExportService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testgetEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(anyLong())).thenReturn(new Employee());
        mockMvc.perform(get("/employees/11")).andExpect(status().isOk());
    }

    @Test
    public void testgetAllEmployees() throws Exception {
        Employee empl = new Employee();
        when(employeeService.getAllEmployees()).thenReturn(List.of(empl));
        mockMvc.perform(get("/employees")).andExpect(status().isOk());
    }

    @Test
    public void testexport() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
        LocalDate specific_yeardate = LocalDate.of(EMPL_START_YEAR, EMPL_START_MONTH, EMPL_START_DAY);
        when(excelExportService.exportEmployeeDetails(EMPL_DEPT_DIGITAL,specific_yeardate)).thenReturn(byteArrayInputStream);
        mockMvc.perform(get("/employees/export")).andExpect(status().isOk());
    }

    @Test
    public void testupdateEmployeeEndDategreater() throws Exception {
        Long id = 1L;
        Employee empl = new Employee();
        empl.setId(id);
        empl.setFname("Peter");
        empl.setLname("sam");
        empl.setDepartment("Data");
        empl.setEmail_id("peterd@test.com");
        empl.setEmployment_start_date(LocalDate.of(2023, 01, 01));
        empl.setEmployment_end_date(LocalDate.of(2025,12,12));
        employeeService.saveEmployee(empl);
        when(employeeService.getEmployeeById(anyLong())).thenReturn(empl);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/{id}",id)
                        .content("{\n" +
                                "        \"fname\": \"AlwindEnGrt\",\n" +
                                "        \"lname\": \"Rodrigez\",\n" +
                                "        \"email_id\": \"test@test.com\",\n" +
                                "        \"department\": \"Digital\",\n" +
                                "        \"employment_start_date\": \"2024-02-03\",\n" +
                                "        \"employment_end_date\": \"2023-02-12\"\n" +
                                "    }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testupdateEmployeeEndDatelesser() throws Exception {
        Long id = 2L;
        Employee empl = new Employee();
        empl.setId(id);
        empl.setFname("Peter");
        empl.setLname("sam");
        empl.setDepartment("Data");
        empl.setEmail_id("peterd@test.com");
        empl.setEmployment_start_date(LocalDate.of(2021, 01, 01));
        empl.setEmployment_end_date(LocalDate.of(2023,12,12));
        employeeService.saveEmployee(empl);
        when(employeeService.getEmployeeById(anyLong())).thenReturn(empl);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/{id}",id)
                        .content("{\n" +
                                "        \"fname\": \"AlwinEdLes\",\n" +
                                "        \"lname\": \"Rodrigez\",\n" +
                                "        \"email_id\": \"test@test.com\",\n" +
                                "        \"department\": \"Digital\",\n" +
                                "        \"employment_start_date\": \"2024-02-03\",\n" +
                                "        \"employment_end_date\": \"2023-02-12\"\n" +
                                "    }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testupdateEmployeeLeftOrg() throws Exception {
        Long id = 3L;
        Employee empl = new Employee();
        empl.setId(id);
        empl.setFname("Peter");
        empl.setLname("sam");
        empl.setDepartment("Data");
        empl.setEmail_id("peterd@test.com");
        empl.setEmployment_start_date(LocalDate.of(2018, 01, 01));
        empl.setEmployment_end_date(LocalDate.of(2022, 01, 01));
        employeeService.saveEmployee(empl);
        when(employeeService.getEmployeeById(anyLong())).thenReturn(empl);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/{id}",id)
                        .content("{\n" +
                                "        \"fname\": \"AlwinEdLes\",\n" +
                                "        \"lname\": \"Rodrigez\",\n" +
                                "        \"email_id\": \"test@test.com\",\n" +
                                "        \"department\": \"Digital\",\n" +
                                "        \"employment_start_date\": \"2024-02-03\",\n" +
                                "        \"employment_end_date\": \"2023-02-12\"\n" +
                                "    }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }


    @Test
    public void testupdateEmployeeEndDateNA() throws Exception {
        Long id = 4L;
        Employee empl = new Employee();
        empl.setId(id);
        empl.setFname("Peter");
        empl.setLname("sam");
        empl.setDepartment("Data");
        empl.setEmail_id("peterd@test.com");
        empl.setEmployment_start_date(LocalDate.of(2018, 01, 01));
        empl.setEmployment_end_date(null);
        when(employeeService.getEmployeeById(id)).thenReturn(empl);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/employees/{id}",id)
                        .content("{\n" +
                                "        \"fname\": \"AlwindEnGrt\",\n" +
                                "        \"lname\": \"Rodrigez\",\n" +
                                "        \"email_id\": \"test@test.com\",\n" +
                                "        \"department\": \"Digital\",\n" +
                                "        \"employment_start_date\": \"2024-02-03\",\n" +
                                "        \"employment_end_date\": \"2023-02-12\"\n" +
                                "    }")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testcreateEmployee() throws Exception {
        Long id = 5L;
        Employee empl = new Employee();
        empl.setId(id);
        empl.setFname("Peter");
        empl.setLname("sam");
        empl.setDepartment("Data");
        empl.setEmail_id("peterd@test.com");
        empl.setEmployment_start_date(LocalDate.of(2018, 01, 01));
        empl.setEmployment_end_date(null);
        ResponseEntity<Employee> response = employeeController.createEmployee(empl);
        assertNotNull(response);
    }

    @Test
    public void deleteEmployeeById() throws Exception {
        Long employeeId = 1L;
        doNothing().when(employeeService).deleteEmployee(employeeId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", employeeId).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        verify(employeeService, times(1)).deleteEmployee(employeeId);
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long id = 100L;
        doNothing().when(employeeService).deleteEmployee(id);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, employeeController.deleteEmployee(id).getStatusCode());
    }
}
