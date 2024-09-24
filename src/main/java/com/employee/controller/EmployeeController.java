package com.employee.controller;

import com.employee.exception.NoSuchEmployeeExistsException;
import com.employee.model.Employee;
import com.employee.service.EmployeeHelperService;
import com.employee.service.EmployeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static com.employee.constants.EmployeeConst.*;

/**
 * This Employee controller class to perform CRUD operations on the Employee table.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeHelperService excelExportService;

    /**
     * Get details of Employee.
     * @return List of Employee
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Begin Get All Employee details");
        List<Employee> lsempl= employeeService.getAllEmployees();
        return ResponseEntity.of(Optional.of(lsempl));
    }

    /**
     * To download employee details as XLS.
     * @return input streamResource for XL download
     */
    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportUsersToExcel() {
        logger.info("Begin Export");
        LocalDate specific_yeardate = LocalDate.of(EMPL_START_YEAR, EMPL_START_MONTH, EMPL_START_DAY);
        String specific_department =EMPL_DEPT_DIGITAL;
        try {
            ByteArrayInputStream in = excelExportService.exportEmployeeDetails(specific_department,specific_yeardate);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=users.xlsx");
            logger.info("Preparing Response Entity");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(in));
        }
         catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get Employee details with a specific Id.
     * @param id
     * @return Employee
     */
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        logger.info("Getting Employee details for"+id);
        Employee empl =employeeService.getEmployeeById(id);
        Optional.ofNullable(empl).orElseThrow(() -> new NoSuchEmployeeExistsException(HttpStatus.NOT_FOUND,"Employee does not exist"));
        return empl;
    }

    /**
     * Create Employee details.
     * @param employee
     * @return Employee
     */
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        logger.info("Begin createEmployee");
        try{
            Employee empl = employeeService.saveEmployee(employee);
            return ResponseEntity.of(Optional.of(empl));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Update Employee details.
     * @param id
     * @param employee
     * @return Employee
     */
    @PutMapping("/{id}")
    public ResponseEntity updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        logger.info("Begin updateEmployee");
        try{
                Employee empl_existing= employeeService.getEmployeeById(id);
                LocalDate emplEndDate = empl_existing.getEmployment_end_date();
                logger.info("emplEndDate :"+emplEndDate);
                if(emplEndDate != null ){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                    Date emplendDatedb = sdf.parse(emplEndDate.toString());
                    logger.info("emplendDatedb :"+emplendDatedb);
                    String currentDatestr = sdf.format(new Date());
                    Date currentdate = sdf.parse(currentDatestr);
                    logger.info("currentdateparse :"+currentdate);
                    if(emplendDatedb.compareTo(currentdate) > 0) {
                        employee.setId(id);
                        Employee updateEmpl = employeeService.saveEmployee(employee);
                        return ResponseEntity.ok().body(updateEmpl);
                    }
                    else{
                        logger.info("Employee -" +id+" left the Organization");
                        return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Employee has left the Organization");
                    }
                    }
                else {
                    employee.setId(id);
                    Employee updateEmpl = employeeService.saveEmployee(employee);
                    return ResponseEntity.ok().body(updateEmpl);

                }

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


        /**
     * Delete Employee details.
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        logger.info("Begin deleteEmployee");
        try{
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
