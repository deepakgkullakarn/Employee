package com.employee.service;

import com.employee.model.Employee;
import com.employee.repo.EmployeeRepo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * This EmployeeHelper Service class called from Employee controller.
 */
@Service
public class EmployeeHelperService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeHelperService.class);
    @Autowired
    private EmployeeRepo employeeRepository;

    /**
     * Export details of Employee for a specific condition.
     * @return ByteArrayInputStream of List of Employees to download as XLS
     */
    public ByteArrayInputStream exportEmployeeDetails(String dept,LocalDate specificDate) throws IOException {
        logger.info("exportAllUsersToExcel ::"+specificDate);
        List<Employee> emploees = employeeRepository.findAllEmployeeToExport(dept,specificDate);
        logger.info("List of emploees found::");
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employee");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("FName");
            headerRow.createCell(2).setCellValue("LName");
            headerRow.createCell(3).setCellValue("EmailId");
            headerRow.createCell(4).setCellValue("Department");
            headerRow.createCell(5).setCellValue("EmploymentStartDate");
            headerRow.createCell(6).setCellValue("EmploymentEndDate");

            // Create data rows
            int rowIdx = 1;
            for (Employee emp : emploees) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(emp.getId());
                row.createCell(1).setCellValue(emp.getFname());
                row.createCell(2).setCellValue(emp.getLname());
                row.createCell(3).setCellValue(emp.getEmail_id());
                row.createCell(4).setCellValue(emp.getDepartment());
                row.createCell(5).setCellValue(emp.getEmployment_start_date());
                row.createCell(6).setCellValue(emp.getEmployment_end_date());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }

    }

}
