package com.pc.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Apache POI工具类
 * Created by Switch on 2017/4/19.
 */
public class POIUtils {

    public static List<List<String>> readXLSXToList(String fileName) throws IOException {
        InputStream is = new FileInputStream(fileName);
        // 通过文件流获取工作簿对象
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        // 获取第一页
        XSSFSheet sheet = workbook.getSheetAt(0);
        return readSheetToList(sheet);

    }

    private static List<List<String>> readSheetToList(XSSFSheet sheet) {
        List<List<String>> table = new ArrayList<>();
        // 循环获取每行
        for (Row row : sheet) {
            // 循环获取每个单元格
            List<String> list = readRowToList(row);
            table.add(list);
        }
        return table;
    }

    private static List<String> readRowToList(Row row) {
        List<String> list = new ArrayList<>();
        for (Cell cell : row) {
            // 获取每个单元格的值
            int cellType = cell.getCellType();
            if (cellType == Cell.CELL_TYPE_NUMERIC) {
                double value = cell.getNumericCellValue();
                list.add(String.valueOf(value));
            } else {
                list.add(cell.getStringCellValue());
            }
        }
        return list;
    }
}
