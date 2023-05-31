package ru.itis.delivery.mappers;

import org.mapstruct.Mapper;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.models.Product;

import java.util.List;

@Mapper
public interface ProductMapper {

    ProductDto toDto(Product product);

    Product toProduct(ProductDto productDto);

    List<ProductDto> toDtoList(List<Product> products);

}
