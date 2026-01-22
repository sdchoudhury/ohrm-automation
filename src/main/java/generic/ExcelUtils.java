package generic;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.EncryptedDocumentException;

import java.io.*;

public class ExcelUtils {

    // -------------------------------------------------
    // Get Sheet (schema preserved)
    // -------------------------------------------------
    public static Sheet getSheet(String filePath, String sheetName)
            throws EncryptedDocumentException, InvalidFormatException, IOException {


        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fis);

        return workbook.getSheet(sheetName);
    }

    /*
     * Read Data from any cell of xls or xlsx file
     */
    public static String getData(String fileName, String sheetName,
                                 int serialNo, int cellNum) throws IOException {

        try {
            Sheet sheet = getSheet(fileName, sheetName);
            Row row = sheet.getRow(serialNo);
            Cell cell = row.getCell(cellNum);

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);

        } catch (Exception e) {
            return "";
        }
    }

    /*
     * Read Data from any range of cell of xls or xlsx file
     */
    public static Object[][] getExcelObjects(String fileName,
                                             String sheetName,
                                             int startRow,
                                             int targetRow,
                                             int startCol,
                                             int targetCol)
            throws EncryptedDocumentException, InvalidFormatException, IOException {

        Sheet sheet = getSheet(fileName, sheetName);
        DataFormatter formatter = new DataFormatter();

        int totalRows = targetRow - startRow + 1;
        int totalCols = targetCol - startCol + 1;

        Object[][] arrayExcelData = new Object[totalRows][totalCols];

        for (int i = startRow; i <= targetRow; i++) {
            for (int j = startCol; j <= targetCol; j++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(j);
                arrayExcelData[i - startRow][j - startCol] =
                        formatter.formatCellValue(cell);
            }
        }
        return arrayExcelData;
    }

    /*
     * Writing Data into any cell of xls or xlsx file
     */
    public static void writeDataIntoCell(String filePath,
                                         String sheetName,
                                         int serialNo,
                                         int cellNo,
                                         String dataToSet)
            throws IOException {


        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook wb = WorkbookFactory.create(fis)) {

            Sheet sheet = wb.getSheet(sheetName);
            Row row = sheet.getRow(serialNo);

            if (row == null) {
                row = sheet.createRow(serialNo);
            }

            Cell cell = row.createCell(cellNo, CellType.STRING);
            cell.setCellValue(dataToSet);

            CellStyle style = wb.createCellStyle();
            Font font = wb.createFont();

            if ("Passed".equalsIgnoreCase(dataToSet)) {
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                font.setColor(IndexedColors.WHITE.getIndex());
            } else if ("Failed".equalsIgnoreCase(dataToSet)) {
                style.setFillForegroundColor(IndexedColors.RED.getIndex());
                font.setColor(IndexedColors.WHITE.getIndex());
            }

            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFont(font);
            cell.setCellStyle(style);

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                wb.write(fos);
            }
        }
    }
}

