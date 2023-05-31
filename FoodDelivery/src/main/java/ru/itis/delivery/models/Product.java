package ru.itis.delivery.models;

import lombok.*;
import ru.itis.delivery.models.enums.ProductState;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_product")
    private String nameOfProduct;
    private Integer price;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameOfProduct='" + nameOfProduct + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

    private String category;
    private String description;
    private Integer calories;

    @OneToMany(mappedBy = "order")
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems;

    @OneToOne
    @JoinColumn(name = "file_id")
    private FileInfo fileInfo;

    @Enumerated(EnumType.STRING)
    private ProductState state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
