package example.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "stocks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"warehouse_id", "item_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @Column(nullable = false)
    private int quantity;

    public Stock(Warehouse warehouse, Item item, int quantity) {
        this.warehouse = warehouse;
        this.item = item;
        this.quantity = quantity;
    }
}