package net.inventorymanagement.inventorymanagementwebservice.dtos;

import java.time.*;
import java.util.*;
import lombok.*;
import net.inventorymanagement.inventorymanagementwebservice.model.*;

@Getter
@Setter
@ToString
public class DetailInventoryItemDTO extends InventoryItemDTO {

    private String droppingReason;
    private String comments;
    private List<Picture> pictures;
    private List<Change> change;
    private Integer droppingQueuePieces;
    private String droppingQueueReason;
    private Integer droppingQueueRequester;
    private LocalDateTime droppingQueueDate;

    // do not remove this
    public DetailInventoryItemDTO() {
    }

    // do not remove this
    public DetailInventoryItemDTO(Integer id, String itemInternalNumber, Type type, String itemName,
                                  String serialNumber, Supplier supplier, Location location,
                                  Integer pieces,
                                  Integer piecesStored, Integer piecesIssued, Integer piecesDropped,
                                  String issuedTo, LocalDateTime deliveryDate,
                                  LocalDateTime issueDate,
                                  LocalDateTime droppingDate, String droppingReason,
                                  String comments,
                                  String status, List<Picture> pictures, List<Change> change,
                                  boolean active,
                                  Department department, String oldItemNumber, String droppingQueue,
                                  Integer droppingQueuePieces, String droppingQueueReason,
                                  Integer droppingQueueRequester, LocalDateTime droppingQueueDate) {
        super(id, itemInternalNumber, type, itemName, serialNumber, supplier, location, pieces,
            piecesStored,
            piecesIssued, piecesDropped, issuedTo, deliveryDate, issueDate, droppingDate, status,
            null, active, department, oldItemNumber, droppingQueue);
        this.droppingReason = droppingReason;
        this.comments = comments;
        this.pictures = pictures;
        this.change = change;
        this.droppingQueuePieces = droppingQueuePieces;
        this.droppingQueueReason = droppingQueueReason;
        this.droppingQueueRequester = droppingQueueRequester;
        this.droppingQueueDate = droppingQueueDate;
    }

    public DetailInventoryItemDTO(InventoryItemDTO dto, String droppingReason, String comments,
                                  List<Picture> pictures, List<Change> change,
                                  Integer droppingQueuePieces, String droppingQueueReason,
                                  Integer droppingQueueRequester, LocalDateTime droppingQueueDate) {
        super(dto);
        this.droppingReason = droppingReason;
        this.comments = comments;
        this.pictures = pictures;
        this.change = change;
        this.droppingQueuePieces = droppingQueuePieces;
        this.droppingQueueReason = droppingQueueReason;
        this.droppingQueueRequester = droppingQueueRequester;
        this.droppingQueueDate = droppingQueueDate;
    }

}
