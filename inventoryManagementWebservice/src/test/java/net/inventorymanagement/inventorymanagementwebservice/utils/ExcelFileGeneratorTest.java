package net.inventorymanagement.inventorymanagementwebservice.utils;

import net.inventorymanagement.inventorymanagementwebservice.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExcelFileGeneratorTest {

    @Test
    void fileHasHeaders() throws IOException {
        byte[] result = ExcelFileGenerator.generateFile(new ArrayList<>(0));
        assertNotNull(result);
        Workbook book = WorkbookFactory.create(new ByteArrayInputStream(result));
        assertNotNull(book);
        Sheet sheet = book.getSheetAt(0);
        assertNotNull(sheet);
        Row row = sheet.getRow(0);
        assertNotNull(row);
        assertEquals(19, row.getLastCellNum());
    }

    @Test
    void exportFile() throws IOException {
        byte[] result = ExcelFileGenerator.generateFile(List.of(generateInventoryItem(), generateInventoryItem()));
        assertNotNull(result);
        Workbook book = WorkbookFactory.create(new ByteArrayInputStream(result));
        assertNotNull(book);
        Sheet sheet = book.getSheetAt(0);
        assertNotNull(sheet);
        assertEquals(2, sheet.getLastRowNum());
    }

    private InventoryItem generateInventoryItem() {
        Category category = new Category();
        Type type = new Type();
        type.setCategory(category);
        Location location = new Location();
        Supplier supplier = new Supplier();
        Department department = new Department();
        Change change = new Change();
        change.setChangeDate(LocalDateTime.now());
        InventoryItem item = new InventoryItem();
        item.setType(type);
        item.setLocation(location);
        item.setPieces(0);
        item.setSupplier(supplier);
        item.setDepartment(department);
        item.setChange(List.of(change));
        return item;
    }
}