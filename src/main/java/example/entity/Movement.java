package example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Getter
@Setter
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_warehouse_id")
    private Warehouse fromWarehouse;

    @ManyToOne
    @JoinColumn(name = "to_warehouse_id")
    private Warehouse toWarehouse;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Movement() {
        this.timestamp = LocalDateTime.now();
    }

    public Movement(Warehouse fromWarehouse, Warehouse toWarehouse, Item item, int quantity) {
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
        this.item = item;
        this.quantity = quantity;
        this.timestamp = LocalDateTime.now();
    }
}