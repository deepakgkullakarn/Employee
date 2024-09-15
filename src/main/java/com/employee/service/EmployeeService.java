package com.employee.service;

import com.employee.model.Employee;
import com.employee.repo.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * This Employee Service class called from Employee controller.
 */
@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepo employeeRepository;

    public List<Employee> getAllEmployees() {
        logger.info("Getting all Employees details");
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        logger.info("Getting Employee details with "+id);
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee saveEmployee(Employee employee) {
        logger.info("Saving Employee details");
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        logger.info("Deleting Employee details with"+id);
        employeeRepository.deleteById(id);
    }
}
