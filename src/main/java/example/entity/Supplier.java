package example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        name = "suppliers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"})
)
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String contactInfo;

    public Supplier() {}

    public Supplier(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }
}