package example.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_warehouse_id")
    private Long fromWarehouseId;

    @Column(name = "to_warehouse_id")
    private Long toWarehouseId;

    @Column(name = "item_id")
    private Long itemId;

    private int quantity;
    private LocalDateTime timestamp;

    public Movement() {}

    public Movement(Long fromWarehouseId, Long toWarehouseId, Long itemId, int quantity) {
        this.fromWarehouseId = fromWarehouseId;
        this.toWarehouseId = toWarehouseId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getFromWarehouseId() { return fromWarehouseId; }
    public void setFromWarehouseId(Long fromWarehouseId) { this.fromWarehouseId = fromWarehouseId; }

    public Long getToWarehouseId() { return toWarehouseId; }
    public void setToWarehouseId(Long toWarehouseId) { this.toWarehouseId = toWarehouseId; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}