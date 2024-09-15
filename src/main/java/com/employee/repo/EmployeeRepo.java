package com.employee.repo;

import com.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

/**
 * This Employee Repo interface for performing CRUD operation on Employee table.
 */
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM EMPLOYEE u WHERE u.DEPARTMENT = :dept and u.employment_start_date > :yeardate", nativeQuery = true)
    public List<Employee> findAllEmployeeToExport(String dept, LocalDate yeardate);

}
