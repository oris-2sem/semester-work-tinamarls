package ru.itis.delivery.services.clazz;

import lombok.extern.slf4j.Slf4j;
import ru.itis.delivery.models.Product;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SessionBag {
    private final Map<Product, Integer> allProducts;

    public SessionBag() {
        allProducts = new HashMap<>();
    }

    public void addOnePositionOfProduct(Product product) {
        if(allProducts.containsKey(product)){
            Integer lastCount = allProducts.get(product);
            allProducts.put(product, lastCount + 1);
        } else {
            allProducts.put(product, 1);
        }
    }

    public Map<Product, Integer> getBag() {
        return allProducts;
    }

    public void show(){
        for(Map.Entry<Product, Integer> entry: allProducts.entrySet()){
            log.info(String.valueOf(entry.getKey()));
            log.info(String.valueOf(entry.getValue()));
        }
    }

    public void deleteOnePositionOfProduct(Product product){
        log.info(allProducts.toString());
        Integer lastCount = allProducts.get(product);
        if(lastCount > 1){
            allProducts.put(product, lastCount - 1);
        }
    }

    public void deleteProduct(Product product){
        allProducts.remove(product);
    }

    public Integer getCountProductInBag(){
        return allProducts.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
