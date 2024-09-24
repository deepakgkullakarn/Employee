package com.employee.service;

import com.employee.model.Employee;
import com.employee.repo.EmployeeRepo;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@SpringBootTest
public class EmployeeHelperServiceTest {

    @Mock
    private EmployeeRepo employeeRepository;

    @InjectMocks
    private EmployeeHelperService employeeHelperService;

    Employee employee = new Employee();
    List<Employee> lsempl = new ArrayList<Employee>();

    @BeforeEach
    public void initialSetEmpl(){
        employee.setId(22L);
        employee.setFname("John");
        employee.setLname("Cen");
        employee.setEmail_id("john.cen@example.com");
        employee.setDepartment("Digital");
        employee.setEmployment_start_date(LocalDate.of(2023,12,31));
        employee.setEmployment_end_date(LocalDate.of(2024,12,31));
        lsempl.add(employee);
    }

    @Test
    public void testexportEmployeeDetails() throws IOException {
        String dept ="Digital";
        LocalDate specificDate =LocalDate.of(2022, 01, 01);
        when(employeeRepository.findAllEmployeeToExport(dept,specificDate)).thenReturn(lsempl);
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Sheet sheet = workbook.createSheet("TestEmployee");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("FName");
        headerRow.createCell(2).setCellValue("LName");
        headerRow.createCell(3).setCellValue("EmailId");
        headerRow.createCell(4).setCellValue("Department");
        headerRow.createCell(5).setCellValue("EmploymentStartDate");
        headerRow.createCell(6).setCellValue("EmploymentEndDate");
        int rowIdx = 1;
        for (Employee emp : lsempl) {
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
        ByteArrayInputStream byteArrayInputStream= employeeHelperService.exportEmployeeDetails(dept,specificDate);
        assertNotNull(workbook);
        assertNotNull(byteArrayInputStream);
    }

}
