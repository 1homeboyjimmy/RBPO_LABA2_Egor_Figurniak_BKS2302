package example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "warehouses",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@Getter
@Setter
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;

    public Warehouse() {}

    public Warehouse(String name, String location) {
        this.name = name;
        this.location = location;
    }
}