package generic;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;

public class ReadExcelFile {

    Workbook wb;
    Sheet sh;

    public ReadExcelFile(String excelPath) {
        try {
            File src = new File(excelPath);
            FileInputStream fis = new FileInputStream(src);
            wb = WorkbookFactory.create(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getData(int sheetNum, int row, int col) {
        sh = wb.getSheetAt(sheetNum);
        return sh.getRow(row).getCell(col).getStringCellValue();
    }

    public int getRowCount(int sheetIndex) {
        return wb.getSheetAt(sheetIndex).getLastRowNum() + 1;
    }
}
