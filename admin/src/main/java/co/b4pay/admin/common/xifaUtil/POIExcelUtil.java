package co.b4pay.admin.common.xifaUtil;

import co.b4pay.admin.entity.DetailDataCard;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POIExcelUtil {
    private static int totalRows = 0;  //总行数
    private static int totalCells = 0; //总列数

    public static Map<String, List<List<String>>> read(String fileName) {
        Map<String, List<List<String>>> maps = new HashMap<String, List<List<String>>>();
        if (fileName == null || !fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
            return maps;
        }
        File file = new File(fileName);
        if (file == null || !file.exists()) {
            return maps;
        }
        try {
            Workbook wb = WorkbookFactory.create(new FileInputStream(file));
            maps = read(wb);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maps;
    }

    public int getTotalRows() {
        return totalCells;
    }

    public int getTolalCells() {
        return totalCells;
    }

    public static Map<String, List<List<String>>> read(Workbook wb) {
        Map<String, List<List<String>>> maps = new HashMap<String, List<List<String>>>();
        int number = wb.getNumberOfSheets();
        if (number > 0) {
            for (int i = 0; i < number; i++) {
                List<List<String>> list = new ArrayList<List<String>>();
                int delnumber = 0;
                Sheet sheet = wb.getSheetAt(i);
                totalRows = sheet.getPhysicalNumberOfRows() - delnumber;
                if (totalRows >= 1 && sheet.getRow(delnumber) != null) {
                    totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
                    for (int j = 0; j < totalRows; j++) {
                        List<String> rowlist = new ArrayList<String>();
                        for (int k = 0; k < totalCells; k++) {
                            if (totalCells > 0) {
                                String value = getCell(sheet.getRow(j).getCell(k));
                                rowlist.add(value);
                            }
                        }
                        list.add(rowlist);
                    }
                }
                maps.put(sheet.getSheetName(), list);
            }
        }
        return maps;
    }

    private static String getCell(Cell cell) {
        String cellValue = null;
        HSSFDataFormatter hssfDataFormatter = new HSSFDataFormatter();
        cellValue = hssfDataFormatter.formatCellValue(cell);  // 使用EXCEL原来格式的方式取得值
        return cellValue;
    }
}










