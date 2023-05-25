package net.inventorymanagement.inventorymanagementwebservice.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import net.inventorymanagement.inventorymanagementwebservice.model.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

/**
 * Example Test to demonstrate that Liquibase sets up an in memory H2 database and fills it
 * with example data located in src/test/resources/config/liquibase/example_data.sql
 */
@SpringBootTest
class InventoryManagementServiceTest {

    @Autowired
    InventoryManagementService inventoryManagementService;

    @Test
    public void shouldGetAllInventoryItems() {
        List<InventoryItem> allItems = inventoryManagementService.getAllInventoryItems();
        assertEquals(1, allItems.size());
    }

    @Test
    public void shouldSetDroppingQueue() throws Exception {
        var item = inventoryManagementService.setDroppingQueue("1", "Deaktivieren");
        assertEquals("Deaktivieren", item.getDroppingQueue());
        item = inventoryManagementService.setDroppingQueue("1", null);
        assertNull(item.getDroppingQueue());
    }

    @Test
    public void shouldSetDroppingQueueSecondTime() throws Exception {
        var item = inventoryManagementService.setDroppingQueue("1", "Deaktivieren");
        assertThrows(Exception.class,
            () -> inventoryManagementService.setDroppingQueue("1", "Deaktivieren"));
        item = inventoryManagementService.setDroppingQueue("1", null);
        assertNull(item.getDroppingQueue());
    }

    @Test
    public void shouldUnSetDroppingQueueSecondTime() throws Exception {
        assertThrows(Exception.class, () -> inventoryManagementService.setDroppingQueue("1", null));
    }
}