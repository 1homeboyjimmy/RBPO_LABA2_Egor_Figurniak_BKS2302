package example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "items",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private String category;

    public Item() {}

    public Item(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}