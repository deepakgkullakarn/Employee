package com.employee.repo;

import com.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepoTest {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Test
    public void testCreate() {

        Employee employee = new Employee();
        employee.setFname("John");
        employee.setLname("Cen");
        employee.setEmail_id("john.cen@example.com");
        employee.setDepartment("Digital");
        employee.setEmployment_start_date(LocalDate.of(2023,12,31));
        employee.setEmployment_end_date(LocalDate.of(2024,12,31));
        employeeRepository.save(employee);
        Iterable<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSize(6).contains(employee);


    }

    @Test
    public void testRead() {
        Employee employee = new Employee();
        employee.setFname("John");
        employee.setLname("Cen");
        employee.setEmail_id("john.cen@example.com");
        employee.setDepartment("Digital");
        employee.setEmployment_start_date(LocalDate.of(2023,12,31));
        employee.setEmployment_end_date(LocalDate.of(2024,12,31));
        Iterable<Employee> employees = employeeRepository.findAll();
        assertThat(employees).hasSizeGreaterThan(0);
    }

    @Test
    public void testDelete() {
        Employee employee = new Employee();
        employee.setFname("John");
        employee.setLname("Cen");
        employee.setEmail_id("john.cen@example.com");
        employee.setDepartment("Digital");
        employee.setEmployment_start_date(LocalDate.of(2023,12,31));
        employee.setEmployment_end_date(LocalDate.of(2024,12,31));
        Long id =1L;
        employeeRepository.deleteById(id);
        Optional<Employee> emp = employeeRepository.findById(id);
        assertThat(emp.isEmpty());
    }
}

