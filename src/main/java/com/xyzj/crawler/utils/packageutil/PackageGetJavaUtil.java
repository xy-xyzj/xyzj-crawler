package com.xyzj.crawler.utils.packageutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author lyy
 * @since 2019-09-18 13:01
 * 读取excel里面的文件到指定目录
 */
public class PackageGetJavaUtil {
    private static final String SRC_PATH = "/Users/liuyangyang/workspace/xyzj/xyzj-crawler";
    private static final String TARGET_PATH = "/Users/liuyangyang/Downloads";
    private final static String EXCEL_2003L = ".xls"; // 2003- 版本的excel
    private final static String EXCEL_2007U = ".xlsx"; // 2007+ 版本的excel

    /**
     * 将流中的Excel数据转成List<Map>
     * @param in 输入流
     * @param fileName 文件名（判断Excel版本）
     * @param mapping 字段名称映射
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> parseExcel(InputStream in, String fileName, Map<String, String> mapping) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new Exception("创建Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 返回数据
        List<Map<String, Object>> ls = new ArrayList<Map<String, Object>>();

        // 遍历Excel中所有的sheet
        for (int i = 0; i < work.getNumberOfSheets(); i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null){
                continue;
            }
            // 取第一行标题
            row = sheet.getRow(0);
            String title[] = null;
            if (row != null) {
                title = new String[row.getLastCellNum()];
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    title[y] = (String) getCellValue(cell);
                }
            } else{
                continue;
            }
            // 遍历当前sheet中的所有行
            for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                row = sheet.getRow(j);
                Map<String, Object> m = new HashMap<String, Object>();
                // 遍历所有的列
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    String key = title[y];
                    m.put(mapping.get(key), getCellValue(cell));
                }
                ls.add(m);
            }
        }
        work.close();
        return ls;
    }

    //复制方法
    public static void copy(String filePath, String srcPath, String targetPath) throws Exception {
        //初始化文件复制
        File srcFile=new File(srcPath + filePath);

        //初始化文件目标
        File targetFile=new File(targetPath+filePath);
        if(!targetFile.getParentFile().exists()){
            targetFile.getParentFile().mkdirs();
        }
        //调用文件拷贝的方法
        targetFile.delete();
        Files.copy(srcFile.toPath(), targetFile.toPath());
    }


    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     * @param inStr,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (EXCEL_2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (EXCEL_2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }

    /**
     * 描述：对表格中数值进行格式化
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        Object value = null;
        // 格式化number String字符
        DecimalFormat df = new DecimalFormat("0");
        // 日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
        // 格式化数字
        DecimalFormat df2 = new DecimalFormat("0");
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = df.format(cell.getNumericCellValue());
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    value = df2.format(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                break;
        }
        return value;
    }


    public static void main(String[] args) throws Exception {
        //取得类路径
        File file = new File("/Users/liuyangyang/Downloads/工作簿1.xlsx");
        FileInputStream fis = new FileInputStream(file);
        Map<String, String> m = new HashMap<String, String>();
        m.put("路径", "path");
        List<Map<String, Object>> ls = parseExcel(fis, file.getName(), m);
        Set<String> resultSet = new HashSet<>();
        for (int i = 0; i <ls.size(); i++) {
            if(ls.get(i).get("path").toString()!=""){
                resultSet.add(ls.get(i).get("path").toString());
            }
        }

        //提取java文件
        for (String filePath : resultSet) {
            copy(filePath,SRC_PATH,TARGET_PATH);
        }
        System.out.println("类提取完成");

    }
}
