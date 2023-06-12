package net.inventorymanagement.inventorymanagementwebservice.repositories;

import net.inventorymanagement.inventorymanagementwebservice.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {

    @Query(value = "SELECT * FROM inventory_item i WHERE i.id = :id", nativeQuery = true)
    InventoryItem findByItemId(Integer id);

    @Query(value = "SELECT * FROM inventory_item i WHERE i.department_id = :departmentId", nativeQuery = true)
    List<InventoryItem> findByDepartmentId(Integer departmentId);

    @Query(value = "SELECT * FROM inventory_item i WHERE i.item_internal_number = :itemInternalNumber", nativeQuery = true)
    InventoryItem findByItemInternalNumber(String itemInternalNumber);

    @Query(value = "SELECT i from InventoryItem i " +
            "left join i.change ch " +
            "WHERE " +
            "(:departmentId is null or i .department.id = :departmentId) and " +
            "(:typeId is null or i .type.id = :typeId ) and " +
            "(:categoryId is null or i .type.category.id = :categoryId ) and " +
            "(:locationId is null or i .location.id = :locationId ) and " +
            "(:supplierId is null or i .supplier.id = :supplierId ) and " +
            "(:status is null or i .status = :status ) and " +
            "(:deliveryDateFrom is null or i .deliveryDate >= :deliveryDateFrom ) and " +
            "(:deliveryDateTo is null or i .deliveryDate <= :deliveryDateTo ) and " +
            "(:issueDateFrom is null or i .issueDate >= :issueDateFrom ) and " +
            "(:issueDateTo is null or i .issueDate <= :issueDateTo ) and " +
            "(:droppingDateFrom is null or i .droppingDate >= :droppingDateFrom ) and " +
            "(:droppingDateTo is null or i .droppingDate <= :droppingDateTo ) and " +
            "ch.changeStatus='Inventargegenstand angelegt.' and " +
            "(:changeDateFrom is null or ch.changeDate >= :changeDateFrom ) and " +
            "(:changeDateTo is null or ch.changeDate <= :changeDateTo )")
    List<InventoryItem> findByOptionalParameters(Integer departmentId, Integer typeId, Integer categoryId,
                                                 Integer locationId, Integer supplierId, String status,
                                                 LocalDateTime deliveryDateFrom, LocalDateTime deliveryDateTo,
                                                 LocalDateTime issueDateFrom, LocalDateTime issueDateTo,
                                                 LocalDateTime droppingDateFrom, LocalDateTime droppingDateTo,
                                                 LocalDateTime changeDateFrom, LocalDateTime changeDateTo);

}
