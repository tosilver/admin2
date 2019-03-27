package co.b4pay.admin.common.util.myutil.file.excel;

import co.b4pay.admin.common.util.myutil.bind.Constants;
import co.b4pay.admin.common.util.myutil.file.CommonUtil;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Excel读取相关工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2017-12-06
 * Time: 11:38
 */
public class ExcelReadUtil {


    /**
     * 读取Excel
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Map<Integer, List<List<Object>>> readExcel(File file) throws IOException {
        String postfix = CommonUtil.getPostfix(file.getName());
        if (Constants.File.FILE_POSTFIX_EXCEL2003.equalsIgnoreCase(postfix)) {
            return read2003Excel(file);
        } else if (Constants.File.FILE_POSTFIX_EXCEL2007.equalsIgnoreCase(postfix)) {
            return read2007Excel(file);
        }
        return null;
    }

    /**
     * 读取Excel(单页)
     *
     * @param file
     * @param sheetIndex
     * @return
     * @throws IOException
     */
    public static List<List<Object>> readExcelWithSheetIndex(File file, int sheetIndex) throws IOException {
        String postfix = CommonUtil.getPostfix(file.getName());
        if (Constants.File.FILE_POSTFIX_EXCEL2003.equalsIgnoreCase(postfix)) {
            return read2003Excel(file, sheetIndex);
        } else if (Constants.File.FILE_POSTFIX_EXCEL2007.equalsIgnoreCase(postfix)) {
            return read2007Excel(file, sheetIndex);
        }
        return null;
    }

    /**
     * 读取2003Excel
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static Map<Integer, List<List<Object>>> read2003Excel(File file)
            throws IOException {
        Map<Integer, List<List<Object>>> map = new LinkedHashMap<>();
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < hwb.getNumberOfSheets(); i++) {
            List<List<Object>> list = new LinkedList<>();
            HSSFSheet sheet = hwb.getSheetAt(i);
            sheetDataToList(sheet, list);
            map.put(i, list);
        }

        return map;
    }

    /**
     * 读取2007Excel
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static Map<Integer, List<List<Object>>> read2007Excel(File file)
            throws IOException {
        Map<Integer, List<List<Object>>> map = new LinkedHashMap<>();
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < xwb.getNumberOfSheets(); i++) {
            List<List<Object>> list = new LinkedList<>();
            XSSFSheet sheet = xwb.getSheetAt(i);
            sheetDataToList(sheet, list);
            map.put(i, list);
        }
        return map;
    }

    /**
     * 读取2003Excel(单页)
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static List<List<Object>> read2003Excel(File file, int sheetIndex)
            throws IOException {
        List<List<Object>> list = new LinkedList<>();
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = hwb.getSheetAt(sheetIndex);
        sheetDataToList(sheet, list);
        return list;
    }

    /**
     * 读取2007Excel(单页)
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static List<List<Object>> read2007Excel(File file, int sheetIndex)
            throws IOException {
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
        List<List<Object>> list = new LinkedList<List<Object>>();
        XSSFSheet sheet = xwb.getSheetAt(sheetIndex);
        sheetDataToList(sheet, list);
        return list;
    }

    /**
     * 将sheet页的数据组装成list
     *
     * @param sheet
     * @param list
     */
    private static void sheetDataToList(Sheet sheet, List<List<Object>> list) {
        Object value = null;
        Row row = null;
        Cell cell = null;
        int counter = 0;
        for (int j = sheet.getFirstRowNum(); counter < sheet
                .getPhysicalNumberOfRows(); j++) {
            row = sheet.getRow(j);
            if (row == null) {
                continue;
            } else {
                counter++;
            }
            List<Object> linked = new LinkedList<Object>();
            for (int k = row.getFirstCellNum(); k <= row.getLastCellNum(); k++) {
                cell = row.getCell(k);
                if (cell == null) {
                    continue;
                }
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        value = formatCellValue(cell);
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        value = cell.getBooleanCellValue();
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        value = "";
                        break;
                    default:
                        value = cell.toString();
                }
                linked.add(value);
            }
            list.add(linked);
        }
    }

    /**
     * 格式化Excel的值
     *
     * @param cell
     * @return
     */
    private static Object formatCellValue(Cell cell) {
        Object value = null;
        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
            value = Constants.File.DECIMAL_FORMAT_DF.format(cell.getNumericCellValue());
        } else if ("General".equals(cell.getCellStyle()
                .getDataFormatString())) {
            value = Constants.File.DECIMAL_FORMAT_NF.format(cell.getNumericCellValue());
        } else {
            value = HSSFDateUtil.getJavaDate(cell
                    .getNumericCellValue()).getTime();
        }
        return value;
    }

    public static Map<Integer, String> getSheetName(File file)
            throws IOException {
        String postfix = CommonUtil.getPostfix(file.getName());
        if (Constants.File.FILE_POSTFIX_EXCEL2003.equalsIgnoreCase(postfix)) {
            return getSheetNameOfExcel2003(file);
        } else if (Constants.File.FILE_POSTFIX_EXCEL2007.equalsIgnoreCase(postfix)) {
            return getSheetNameOfExcel2007(file);
        }
        return null;
    }

    /**
     * 获取2003Excel的sheet
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static Map<Integer, String> getSheetNameOfExcel2003(File file)
            throws IOException {
        Map<Integer, String> map = new LinkedHashMap<>();
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            map.put(i, wb.getSheetAt(i).getSheetName());
        }
        return map;
    }

    /**
     * 获取2007Excel的sheet
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static Map<Integer, String> getSheetNameOfExcel2007(File file)
            throws IOException {
        Map<Integer, String> map = new LinkedHashMap<>();
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            map.put(i, wb.getSheetAt(i).getSheetName());
        }
        return map;
    }


}
