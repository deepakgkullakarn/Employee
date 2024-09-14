package com.employee.service;

import com.employee.model.Employee;
import com.employee.repo.EmployeeRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void testgetAllEmployees() {
        List<Employee> lsempl = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(lsempl);
        List<Employee> emplresponse= employeeService.getAllEmployees();
        assertNotNull(emplresponse);


    }

    @Test
    public void testgetEmployeeById() {
        Long id=1L;
        when(employeeRepository.findById(id)).thenReturn(Optional.of(new Employee()));
        Employee emplresponse=employeeService.getEmployeeById(id);
        assertNotNull(emplresponse);
    }

    @Test
    public void saveEmployee() {
        Employee emp = new Employee();
        emp.setId(1l);
        emp.setFname("Test");
        emp.setLname("Testing");
        emp.setDepartment("Digital");
        emp.setEmployment_Start_Date(LocalDate.of(2023,12,31));
        emp.setEmployment_end_date(LocalDate.of(2025,12,31));
        when(employeeRepository.save(emp)).thenReturn(new Employee());
        Employee saverespon=employeeService.saveEmployee(emp);
        assertNotNull(saverespon);
    }
}