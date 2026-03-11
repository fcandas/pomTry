package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelUtils {

    private static Workbook workbook;
    private static Sheet sheet;

    public static void setExcelFile(String path, String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Excel file not found: " + path);
        }
    }

    public static int getRowCount() {
        return sheet.getPhysicalNumberOfRows();
    }

    public static int getColCount() {
        return sheet.getRow(0).getPhysicalNumberOfCells();
    }

    public static String getCellData(int rowNum, int colNum) {   // here we read the data and transform it
        Cell cell = sheet.getRow(rowNum).getCell(colNum);

        if(cell.getCellType() == CellType.STRING){
            return cell.getStringCellValue();
        } else if(cell.getCellType() == CellType.NUMERIC){
            // Sayıları string’e çevir
            return String.valueOf((int)cell.getNumericCellValue());
        } else if(cell.getCellType() == CellType.BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        } else {
            return "";
        }
    }

}