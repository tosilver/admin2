package co.b4pay.admin.common.util.myutil.file.excel;

import co.b4pay.admin.common.util.myutil.bind.Constants;
import co.b4pay.admin.common.util.myutil.file.CommonUtil;
import co.b4pay.admin.common.util.myutil.file.FileDownloadUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom.*;
import org.jdom.input.SAXBuilder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Excel模板相关工具类
 * Created with IntelliJ IDEA
 * Created By AZain
 * Date: 2017-12-06
 * Time: 13:50
 */
public class ExcelTemplateUtil {


    //读取excel文件
    public static List<List<Object>> readExcel(File file) throws Exception {
        String ext = CommonUtil.getPostfix(file.getName());
        if ("xls".equals(ext)) {
            return readExcel(file, "HSSFWorkbook");
        } else if ("xlsx".equals(ext)) {
            return readExcel(file, "XSSFWorkbook");
        }
        return null;
    }

    //创建excel(单页)
    public static Object createExcel(String templatePath, List<Object> datas) throws Exception {
        File file = new File(templatePath);     //获取解析xml文件路径
        SAXBuilder builder = new SAXBuilder();

        Document parse = builder.build(file);    //解析xml文件
        Element root = parse.getRootElement();   //获取xml文件跟节点
        String excelName = root.getAttribute("name").getValue();      //获取模板名称

        String postfix = CommonUtil.getPostfix(excelName);
        if ("xls".equals(postfix)) {
            return createExcel(root, datas, "HSSFWorkbook");
        } else if ("xlsx".equals(postfix)) {
            return createExcel(root, datas, "XSSFWorkbook");
        }
        return null;
    }

    /**
     * 导出excel(单页)
     *
     * @param templatePath 模板名称
     * @param postfix      导出格式
     * @param datas        数据
     */
    public static void exportExcel(String templatePath, String postfix, List datas, HttpServletRequest request, HttpServletResponse response) throws Exception {
        File file = new File(templatePath);             //获取解析xml文件路径
        SAXBuilder builder = new SAXBuilder();
        Document parse = builder.build(file);           //解析xml文件
        Element root = parse.getRootElement();          //获取xml文件跟节点
        String excelName = root.getAttribute("name").getValue();  //获取模板名称

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Workbook workbook = null;
        if ("xls".equals(postfix)) {            //HSSFWorkbook
            workbook = createExcel(root, datas, "HSSFWorkbook");
        }/* else if ("xlsx".equals(postfix)) {   //XSSFWorkbook
            workbook = createExcel(root, datas, "XSSFWorkbook");
        }*/ else if ("xlsx".equals(postfix)) {   //SXSSFWorkbook
            workbook = createExcel(root, datas, "SXSSFWorkbook");
        }
        if (workbook != null) {
            workbook.write(os);
            FileDownloadUtil.downloadToClient(excelName + "." + postfix, os.toByteArray(), request, response);
        }
    }

    //根据需要模板地址以及数据，生成一个excel工作薄：root（xml元素）  datas（数据）
    private static Workbook createExcel(Element root, List datas, String excelType) {
        Workbook wb = null;
        try {
            if ("SXSSFWorkbook".equals(excelType)) {
                wb = new SXSSFWorkbook(100);           // keep 100 rows in memory, exceeding rows will be flushed to disk
            } else if ("XSSFWorkbook".equals(excelType)) {
                wb = new XSSFWorkbook();
            } else if ("HSSFWorkbook".equals(excelType)) {
                wb = new HSSFWorkbook();
            }
            if (wb != null) {
                Sheet sheet = wb.createSheet("Sheet0");        //创建sheet

                int rownum = 0;
                int column = 0;
                //设置列宽
                Element colGroup = root.getChild("colGroup");
                setColumnWidth(sheet, colGroup);

                //设置标题
                Element title = root.getChild("title");
                List trs = title.getChildren("tr");
                for (int i = 0; i < trs.size(); i++) {
                    Element tr = (Element) trs.get(i);
                    float trHeight = tr.getAttribute("height").getFloatValue();//行高
                    List tds = tr.getChildren("td");
                    Row row = sheet.createRow(rownum);
                    row.setHeightInPoints(trHeight);
                    CellStyle cellStyle = wb.createCellStyle();
                    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                    for (column = 0; column < tds.size(); column++) {
                        Element td = (Element) tds.get(column);
                        Cell cell = row.createCell(column);
                        Attribute rowSpan = td.getAttribute("rowSpan");
                        Attribute colSpan = td.getAttribute("colSpan");
                        Attribute value = td.getAttribute("value");
                        if (value != null) {
                            String val = value.getValue();
                            cell.setCellValue(val);
                            int rSpan = rowSpan.getIntValue() - 1;
                            int cSpan = colSpan.getIntValue() - 1;

                            //设置字体
                            Font font = wb.createFont();
                            font.setFontName("仿宋_GB2312");
                            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
                            font.setFontHeightInPoints((short) 12);
                            cellStyle.setFont(font);
                            cell.setCellStyle(cellStyle);
                            //合并单元格居中
                            sheet.addMergedRegion(new CellRangeAddress(rSpan, rSpan, 0, cSpan));
                        }
                    }
                    rownum++;
                }
                //设置表头
                Element thead = root.getChild("thead");
                trs = thead.getChildren("tr");
                for (int i = 0; i < trs.size(); i++) {
                    Element tr = (Element) trs.get(i);
                    float trHeight = tr.getAttribute("height").getFloatValue();//行高
                    Row row = sheet.createRow(rownum);
                    row.setHeightInPoints(trHeight);
                    List ths = tr.getChildren("th");
                    for (column = 0; column < ths.size(); column++) {
                        Element th = (Element) ths.get(column);
                        Attribute valueAttr = th.getAttribute("value");
                        Cell cell = row.createCell(column);
                        if (valueAttr != null) {
                            String value = valueAttr.getValue();
                            cell.setCellValue(value);
                        }
                    }
                    rownum++;
                }

                //设置数据区域样式
                Element tbody = root.getChild("tbody");
                Element tr = tbody.getChild("tr");
                float height = tr.getAttribute("height").getFloatValue();
                int repeat = datas.size();

                List tds = tr.getChildren("td");
                for (int i = 0; i < repeat; i++) {
                    Row row = sheet.createRow(rownum);
                    row.setHeightInPoints(height);
                    for (column = 0; column < tds.size(); column++) {
                        Element td = (Element) tds.get(column);
                        Cell cell = row.createCell(column);
                        setValue(td, datas.get(i), cell);       //设置值
                        setType(wb, sheet, cell, td);           //设置样式
                    }
                    rownum++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wb;
    }

    //读取excel
    private static List<List<Object>> readExcel(File file, String excelType) throws Exception {
        List<List<Object>> list = new LinkedList<List<Object>>();
        Workbook wb = null;
        if ("XSSFWorkbook".equals(excelType)) {
            wb = new XSSFWorkbook(new FileInputStream(file));
        } else if ("HSSFWorkbook".equals(excelType)) {
            wb = new HSSFWorkbook(new FileInputStream(file));
        }
        if (wb != null) {
            Sheet sheet = wb.getSheetAt(0);
            Object value = null;
            Row row = null;
            Cell cell = null;
            int counter = 0;
            for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                } else {
                    counter++;
                }
                List<Object> linked = new LinkedList<Object>();
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                    DecimalFormat nf = new DecimalFormat("0.00");                   // 格式化数字
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                                value = nf.format(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            value = cell.getBooleanCellValue();
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            value = "";
                            break;
                        default:
                            value = cell.toString();
                    }
//                if (value == null || "".equals(value)) {
//                    continue;
//                }
                    linked.add(value);
                }
                list.add(linked);
            }
        }
        return list;
    }

    //设置单元格样式  HSSFWorkbook:xls    XSSFWorkbook:xlsx    SXSSFWorkbook:xlsx  大数据量的
    private static void setType(Workbook wb, Sheet sheet, Cell cell, Element td) {
        Attribute typeAttr = td.getAttribute("type");     //类型属性
        Attribute formatAttr = td.getAttribute("format");     //转换格式属性

        String type = td.getAttributeValue("type");                //类型值
        String formatValue = td.getAttributeValue("format");       //转换格式值

        DataFormat format = wb.createDataFormat();
        CellStyle cellStyle = wb.createCellStyle();
        if ("NUMERIC".equalsIgnoreCase(type)) {
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            formatValue = StringUtils.isNotEmpty(formatValue) ? formatValue : "##0.00";
            cellStyle.setDataFormat(format.getFormat(formatValue));
        } else if ("STRING".equalsIgnoreCase(type)) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cellStyle.setDataFormat(format.getFormat("@"));
        } else if ("DATE".equalsIgnoreCase(type)) {
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            formatValue = StringUtils.isNotEmpty(formatValue) ? formatValue : "yyyy-MM-dd HH:mm:ss";
            cellStyle.setDataFormat(format.getFormat(formatValue));
        } else if ("ENUM".equalsIgnoreCase(type)) {
//            if (cell instanceof HSSFCell) {
//                CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
//                DVConstraint constraint = DVConstraint.createExplicitListConstraint(formatValue.split(","));         //加载下拉列表内容
//                HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);                  //数据有效性对象
//                sheet.addValidationData(dataValidation);
//            } else if (cell instanceof XSSFCell) {
//                CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
//                //加载下拉列表内容
//                XSSFDataValidationHelper helper = new XSSFDataValidationHelper((XSSFSheet) sheet);
//                XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) helper.createExplicitListConstraint(formatValue.split(","));
//                //数据有效性对象
//                XSSFDataValidation dataValidation = (XSSFDataValidation) helper.createValidation(constraint, regions);
//                sheet.addValidationData(dataValidation);
//            }
        }
        cell.setCellStyle(cellStyle);
    }

    //设置单元格值  HSSFSheet:xls    XSSFSheet:xlsx    SXSSFSheet:xlsx  大数据量的
    private static void setValue(Element td, Object object, Cell cell) {
        String type = td.getAttributeValue("type");            //数值类型
        String format = td.getAttributeValue("format");       //格式

        String property = td.getAttributeValue("property");
        String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);            //拼装getter方法
        try {
            //利用反射机制获取dataSet中的属性值，填进cell中
            Class<? extends Object> clazz = object.getClass();
            Method getMethod = clazz.getMethod(getMethodName, new Class[]{});
            Object value = getMethod.invoke(object, new Object[]{}); //调用getter从data中获取数据
            value = (value == null) ? "" : value;

            cell.setCellValue(value.toString());
            if (value instanceof Double) {
                cell.setCellValue((Double) value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //设置列宽  HSSFSheet:xls    XSSFSheet:xlsx    SXSSFSheet:xlsx  大数据量的
    private static void setColumnWidth(Sheet sheet, Element colGroup) {
        List cols = colGroup.getChildren("col");
        for (int i = 0; i < cols.size(); i++) {
            Element col = (Element) cols.get(i);
            Attribute widthAttr = col.getAttribute("width");
            String width = col.getAttributeValue("width");
            String unit = width.replaceAll("[0-9,\\.]", "");
            String value = width.replaceAll(unit, "");
            int v = 0;
            if (StringUtils.isEmpty(unit) || "px".endsWith(unit)) {
                v = Math.round(Float.parseFloat(value) * 37F);
            } else if ("em".endsWith(unit)) {
                v = Math.round(Float.parseFloat(value) * 267.5F);
            }
            sheet.setColumnWidth(i, v);
        }
    }

}
