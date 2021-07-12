package uz.mk.communicationcompanyservice.service;

import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import uz.mk.communicationcompanyservice.entity.Detail;
import uz.mk.communicationcompanyservice.payload.ApiResponse;
import uz.mk.communicationcompanyservice.repository.DetailRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class DetailService {
    @Autowired
    DetailRepository detailRepository;

    @SneakyThrows
    public void downloadDetails(HttpServletResponse response) {
        List<Detail> details = detailRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Details");

        Row header = sheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell = header.createCell(1);
        headerCell.setCellValue("name");
        headerCell = header.createCell(2);
        headerCell.setCellValue("description");
        headerCell = header.createCell(3);
        headerCell.setCellValue("date");
        headerCell = header.createCell(4);
        headerCell.setCellValue("client move type id");
        headerCell = header.createCell(5);
        headerCell.setCellValue("simcard id");

        for (int i = 0; i < details.size(); i++) {
            Detail currDetail = details.get(i);

            Row row = sheet.createRow(i + 2);
            Cell cell = row.createCell(0);
            cell.setCellValue(currDetail.getId().toString());
            cell = row.createCell(1);
            cell.setCellValue(currDetail.getName());
            cell = row.createCell(2);
            cell.setCellValue(currDetail.getDescription());
            cell = row.createCell(3);
            cell.setCellValue(currDetail.getDate());
            cell = row.createCell(4);
            cell.setCellValue(currDetail.getClientMoveType().getId().toString());
            cell = row.createCell(5);
            cell.setCellValue(currDetail.getSimcard().getId().toString());
        }

        File currDir = new File("uploadsFiles");
        String path = currDir.getAbsolutePath();
        Path fileLocation = Paths.get(path + "/" + "details.xlsx");

        FileOutputStream outputStream = new FileOutputStream(String.valueOf(fileLocation));
        workbook.write(outputStream);

        workbook.close();

        response.setHeader("Content-Disposition",
                "attachment; filename=\"details.xlsx\"");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        FileInputStream inputStream = new FileInputStream(new File(String.valueOf(fileLocation)));

        try {
            FileCopyUtils.copy(inputStream , response.getOutputStream());
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }
    }
}
