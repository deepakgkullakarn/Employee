package com.employee.repo;

import com.employee.model.Employee;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepository;

    Employee employee = new Employee();

    @BeforeAll
    public void initialSetup()
    {
        employee.setFname("John");
        employee.setLname("Doe");
        employee.setEmailId("john.doe@example.com");
        employee.setDepartment("john.doe@example.com");
        employee.setEmployment_Start_Date(LocalDate.of(2023,12,31));
        employee.setEmployment_end_date(LocalDate.of(2024,12,31));
    }

    @Test
    public void testCreateReadDelete() {
        employeeRepository.save(employee);
        Iterable<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(1).contains(employee);

        employeeRepository.deleteAll();
        assertThat(employeeRepository.findAll()).isEmpty();
    }
}

