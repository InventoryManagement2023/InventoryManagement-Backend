package net.inventorymanagement.inventorymanagementwebservice.utils;

import net.inventorymanagement.inventorymanagementwebservice.model.InventoryItem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class ExcelFileGenerator {

    public static byte[] generateFile(List<InventoryItem> items){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventory Items");

        Row titels = sheet.createRow(0);
        titels.createCell(0).setCellValue("Name");
        titels.createCell(1).setCellValue("Seriennummer");

        int rowIndex = 1;
        for (InventoryItem item: items) {
            Row itemRow = sheet.createRow(rowIndex);
            itemRow.createCell(0).setCellValue(item.getItemName());
            itemRow.createCell(1).setCellValue(item.getSerialNumber());
            rowIndex++;
        }
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        try {
            workbook.write(fos);
        }
        catch (Exception e){
           System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return fos.toByteArray();
    }

}
