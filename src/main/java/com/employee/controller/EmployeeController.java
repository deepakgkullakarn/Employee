package com.employee.controller;


import com.employee.exception.EmployeeExportException;
import com.employee.exception.NoSuchEmployeeExistsException;
import com.employee.model.Employee;
import com.employee.service.EmployeeHelperService;
import com.employee.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeHelperService excelExportService;

/*    @GetMapping("/display")
    public String getAllResponses(Model model) {
        model.addAttribute("responses", employeeService.getAllEmployees());
        return "employeeDetails";
    }*/

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> lsempl= employeeService.getAllEmployees();
        return ResponseEntity.of(Optional.of(lsempl));
    }

    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> exportUsersToExcel() {
        System.out.println("emplyeeExport ---------------------------------");
        try {
            ByteArrayInputStream in = excelExportService.exportAllUsersToExcel();
            System.out.println("in ---------------------------------" + in);
            HttpHeaders headers = new HttpHeaders();
            System.out.println("headers" + headers);
            headers.add("Content-Disposition", "attachment; filename=users.xlsx");
            System.out.println("Preparing Response Entity ---------------------------------");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(new InputStreamResource(in));
        }
        catch (EmployeeExportException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (IOException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        Employee empl =employeeService.getEmployeeById(id);
        Optional.ofNullable(empl).orElseThrow(() -> new NoSuchEmployeeExistsException(HttpStatus.NOT_FOUND,"Employee does not exist"));
        return empl;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try{
            Employee empl = employeeService.saveEmployee(employee);
            return ResponseEntity.of(Optional.of(empl));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        try{
                employee.setId(id);
                Employee updateEmpl = employeeService.saveEmployee(employee);
                return ResponseEntity.ok().body(updateEmpl);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try{
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
