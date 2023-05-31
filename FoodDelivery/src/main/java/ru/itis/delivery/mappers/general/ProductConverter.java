package ru.itis.delivery.mappers.general;

import org.springframework.stereotype.Component;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.dto.product.RequestProductForm;
import ru.itis.delivery.models.Product;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {

    public Product productFromRequest(RequestProductForm requestProductForm){
        return Product.builder()
                .nameOfProduct(requestProductForm.getNameOfProduct())
                .category(requestProductForm.getCategory())
                .price(requestProductForm.getPrice())
                .calories(requestProductForm.getCalories())
                .description(requestProductForm.getDescription())
                .build();
    }

    public ProductDto toDto(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .calories(product.getCalories())
                .category(product.getCategory())
                .description(product.getDescription())
                .nameOfProduct(product.getNameOfProduct())
                .price(Double.valueOf(product.getPrice()))
                .photoName(product.getFileInfo().getStorageFileName())
                .build();
    }

    public List<ProductDto> toDtoList(List<Product> products){
        return products.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
