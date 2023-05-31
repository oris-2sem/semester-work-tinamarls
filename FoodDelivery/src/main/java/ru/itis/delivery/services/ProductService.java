package ru.itis.delivery.services;

import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.dto.product.RequestProductForm;
import ru.itis.delivery.models.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ProductDto save(RequestProductForm newProduct);

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, RequestProductForm requestProductForm);

    List<ProductDto> findAllActive();

    void delete(Long productId);

    Map<String, List<ProductDto>> getAllProductsByCategories();

    Product findProductById(Long id);
}
