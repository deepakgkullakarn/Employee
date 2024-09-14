package com.employee.service;

import com.employee.model.Employee;
import com.employee.repo.EmployeeRepo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeHelperService {
    @Autowired
    private EmployeeRepo employeeRepository;

    public ByteArrayInputStream exportAllUsersToExcel() throws IOException {
        LocalDate specificDate = LocalDate.of(2023, 12, 31);
        System.out.println("exportAllUsersToExcel ::"+specificDate);
        List<Employee> emploees = employeeRepository.findAllEmployeeToExport(specificDate);
        System.out.println("emploees ::---------------------------->"+emploees);
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employee");

            // Create header row
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
                row.createCell(3).setCellValue(emp.getEmailId());
                row.createCell(4).setCellValue(emp.getDepartment());
                row.createCell(5).setCellValue(emp.getEmployment_Start_Date());
                row.createCell(6).setCellValue(emp.getEmployment_end_date());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }


}
