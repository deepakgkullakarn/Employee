package com.employee.controller;


import com.employee.model.Employee;
import com.employee.service.EmployeeHelperService;
import com.employee.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeHelperService excelExportService;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testgetAllEmployees(){
        when(employeeService.getAllEmployees()).thenReturn(new ArrayList<Employee>());
        ResponseEntity<List<Employee>> emplresponse=employeeController.getAllEmployees();
        assertNotNull(emplresponse);
    }

    @Test
    public void testgetexportUsersToExcel() throws IOException {
        ByteArrayOutputStream byteArrayopStream = new ByteArrayOutputStream();
        when(excelExportService.exportAllUsersToExcel()).thenReturn(new ByteArrayInputStream(byteArrayopStream.toByteArray()));
        ResponseEntity<InputStreamResource> response=employeeController.exportUsersToExcel();
        assertNotNull(response);

    }

    @Test
    public void testgetEmployeeById(){
        Long id=1L;
        when(employeeService.getEmployeeById(id)).thenReturn(new Employee());
        Employee emplresponse=employeeController.getEmployeeById(id);
        assertNotNull(emplresponse);
    }

    @Test
    public void testcreateEmployee(){
        Employee emp = new Employee();
        when(employeeService.saveEmployee(emp)).thenReturn(new Employee());
        ResponseEntity<Employee> createresponse=employeeController.createEmployee(new Employee());
        assertNotNull(createresponse);
    }

    @Test
    public void testupdateEmployee(){
        Long id=1L;
        Employee emp = new Employee();
        emp.setId(id);
        when(employeeService.saveEmployee(emp)).thenReturn(new Employee());
        ResponseEntity<Employee> createresponse=employeeController.updateEmployee(id,new Employee());
        assertNotNull(createresponse);
    }

}
