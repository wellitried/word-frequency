package services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

class ExcelMaker {

    InputStream getExcelStream(Map<String, Integer> map) {
        HSSFWorkbook workbook = makeExcel(map);
        InputStream dictionary = new ByteArrayInputStream(workbook.getBytes());
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dictionary;
    }

    private HSSFWorkbook makeExcel(Map<String, Integer> map) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row titleRow = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        Cell cell = titleRow.createCell(0);
        cell.setCellValue("Word");
        cell.setCellStyle(style);

        cell = titleRow.createCell(1);
        cell.setCellValue("Frequency");
        cell.setCellStyle(style);

        Iterator iterator = map.entrySet().iterator();
        int rowIndex = 1;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iterator.next();
            Row row = sheet.createRow(rowIndex);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
            rowIndex++;
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        return workbook;
    }
}