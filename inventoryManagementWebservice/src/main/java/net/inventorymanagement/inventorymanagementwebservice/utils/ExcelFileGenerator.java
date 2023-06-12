package net.inventorymanagement.inventorymanagementwebservice.utils;

import net.inventorymanagement.inventorymanagementwebservice.model.InventoryItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class ExcelFileGenerator {

    public static byte[] generateFile(List<InventoryItem> items) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inventory Items");
        Row titels = sheet.createRow(0);

        titels.createCell(0).setCellValue("Inventarnummer");
        titels.createCell(1).setCellValue("Kategorie");
        titels.createCell(2).setCellValue("Typ");
        titels.createCell(3).setCellValue("Beschreibung");
        titels.createCell(4).setCellValue("Status");
        titels.createCell(5).setCellValue("Standort");
        titels.createCell(6).setCellValue("Stück");
        titels.createCell(7).setCellValue("La / Ag / As*");
        titels.createCell(8).setCellValue("Alte Inventarnr.");
        titels.createCell(9).setCellValue("Seriennummer");
        titels.createCell(10).setCellValue("Lieferdatum");
        titels.createCell(11).setCellValue("Ausgabedatum");
        titels.createCell(12).setCellValue("Ausgegeben an");
        titels.createCell(13).setCellValue("Lieferant");
        titels.createCell(14).setCellValue("Abteilung");
        titels.createCell(15).setCellValue("Ausscheidedatum");
        titels.createCell(16).setCellValue("Letzte Änderung");
        titels.createCell(17).setCellValue("Anmerkung");
        titels.createCell(18).setCellValue("Ausscheidegrund");

        int rowIndex = 1;
        for (InventoryItem item : items) {
            Row itemRow = sheet.createRow(rowIndex);
            itemRow.createCell(0).setCellValue(item.getItemInternalNumber());
            itemRow.createCell(1).setCellValue(item.getType().getCategory().getCategoryName());
            itemRow.createCell(2).setCellValue(item.getType().getTypeName());
            itemRow.createCell(3).setCellValue(item.getItemName());
            itemRow.createCell(4).setCellValue(item.getStatus());
            itemRow.createCell(5).setCellValue(item.getLocation().getLocationName());
            itemRow.createCell(6).setCellValue(item.getPieces());
            itemRow.createCell(7).setCellValue(item.getPiecesStored() + "/"
                    + item.getPiecesIssued() + "/"
                    + item.getPiecesDropped());
            itemRow.createCell(8).setCellValue(item.getOldItemNumber());
            itemRow.createCell(9).setCellValue(item.getSerialNumber());
            CellStyle datestyle = workbook.createCellStyle();
            datestyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd.MM.yyyy"));
            Cell deliveryDate = itemRow.createCell(10);
            deliveryDate.setCellValue(item.getDeliveryDate());
            deliveryDate.setCellStyle(datestyle);
            Cell issueDate = itemRow.createCell(11);
            issueDate.setCellValue(item.getIssueDate());
            issueDate.setCellStyle(datestyle);
            itemRow.createCell(12).setCellValue(item.getIssuedTo());
            itemRow.createCell(13).setCellValue(item.getSupplier().getSupplierName());
            itemRow.createCell(14).setCellValue(item.getDepartment().getDepartmentName());
            Cell droppingDate = itemRow.createCell(15);
            droppingDate.setCellValue(item.getDroppingDate());
            droppingDate.setCellStyle(datestyle);
            Cell changeDate = itemRow.createCell(16);
            changeDate.setCellValue(item.getLastChange().getChangeDate());
            changeDate.setCellStyle(datestyle);
            itemRow.createCell(17).setCellValue(item.getComments());
            itemRow.createCell(18).setCellValue(item.getDroppingReason());
            rowIndex++;
        }
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        try {
            workbook.write(fos);
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return fos.toByteArray();
    }

}
