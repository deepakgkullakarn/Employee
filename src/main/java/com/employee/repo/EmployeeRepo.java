package com.employee.repo;

import com.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM EMPLOYEE u WHERE u.DEPARTMENT = 'Digital' and u.employment_start_date > :year", nativeQuery = true)
    public List<Employee> findAllEmployeeToExport(LocalDate year);

}
