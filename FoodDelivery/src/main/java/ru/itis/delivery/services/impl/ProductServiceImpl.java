package ru.itis.delivery.services.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.itis.delivery.dto.product.ProductDto;
import ru.itis.delivery.dto.product.RequestProductForm;
import ru.itis.delivery.exceptions.NotFoundException;
import ru.itis.delivery.mappers.general.ProductConverter;
import ru.itis.delivery.models.FileInfo;
import ru.itis.delivery.models.Product;
import ru.itis.delivery.models.enums.ProductState;
import ru.itis.delivery.repositories.ProductRepository;
import ru.itis.delivery.services.FileService;
import ru.itis.delivery.services.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;

    ProductConverter productConverter;

    FileService fileService;


    @Override
    public ProductDto save(RequestProductForm newProduct) {
        Product productForSave =  productConverter.productFromRequest(newProduct);

        FileInfo file = fileService.saveFile(newProduct.getPhoto());

        productForSave.setFileInfo(file);
        productForSave.setState(ProductState.ACTIVE);

        Product product = productRepository.save(productForSave);

        return productConverter.toDto(product);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = getProductOrThrow(productId);

        return productConverter.toDto(product);
    }

    @Override
    public ProductDto updateProduct(Long productId, RequestProductForm requestProductForm) {
        Product product = getProductOrThrow(productId);

        Product productUpdate =  productConverter.productFromRequest(requestProductForm);

        if(Objects.nonNull(productUpdate.getNameOfProduct())
                && !productUpdate.getNameOfProduct().isEmpty()){
            product.setNameOfProduct(productUpdate.getNameOfProduct());
        }

        if(Objects.nonNull(productUpdate.getCategory())
                && !productUpdate.getCategory().isEmpty()){
            product.setCategory(productUpdate.getCategory());
        }

        if(Objects.nonNull(productUpdate.getDescription())
                && !productUpdate.getDescription().isEmpty()){
            product.setDescription(productUpdate.getDescription());
        }

        if(Objects.nonNull(productUpdate.getCalories())
                && productUpdate.getCalories() != 0){
            product.setCalories(productUpdate.getCalories());
        }

        if(Objects.nonNull(productUpdate.getPrice())
                && productUpdate.getPrice() != 0){
            product.setPrice(productUpdate.getPrice());
        }

        if(Objects.nonNull(requestProductForm.getPhoto())
                && !requestProductForm.getPhoto().getName().isEmpty()){

            String fileName = product.getFileInfo().getStorageFileName();
            product.setFileInfo(null);

            FileInfo fileInfo = fileService.updateFile(fileName,
                    requestProductForm.getPhoto());

            product.setFileInfo(fileInfo);
        }

        productRepository.save(product);

        return productConverter.toDto(product);

    }

    @Override
    public List<ProductDto> findAllActive() {
        List<Product> products = productRepository.findAllByState(ProductState.ACTIVE);
        return productConverter.toDtoList(products);
    }

    @Override
    public void delete(Long productId) {
        Product product = getProductOrThrow(productId);

        product.setState(ProductState.DELETED);

        productRepository.save(product);
    }

    @Override
    public Map<String, List<ProductDto>> getAllProductsByCategories() {

        Map<String, List<ProductDto>> allProductsWithCategory = new HashMap<>();

        List<String> categories = productRepository.findDistinctCategories();

        for(String category:categories){
            List<Product> products = productRepository.findAllByCategoryAndState(category, ProductState.ACTIVE);
            allProductsWithCategory.put(category, productConverter.toDtoList(products));
        }
        return allProductsWithCategory;
    }

    @Override
    public Product findProductById(Long id) {
        return getProductOrThrow(id);
    }

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Позиция меню с идентификатором " + id + " не найдена"));
    }
}
