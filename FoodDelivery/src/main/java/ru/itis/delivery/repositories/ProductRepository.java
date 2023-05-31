package ru.itis.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.itis.delivery.models.Product;
import ru.itis.delivery.models.enums.ProductState;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByState(ProductState state);

    @Query("SELECT DISTINCT p.category FROM Product p where p.state = 'ACTIVE'")
    List<String> findDistinctCategories();

    List<Product> findAllByCategoryAndState(String category, ProductState state);
}
