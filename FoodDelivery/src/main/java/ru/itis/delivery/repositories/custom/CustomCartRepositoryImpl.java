package ru.itis.delivery.repositories.custom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.itis.delivery.models.CartItem;
import ru.itis.delivery.models.Client;
import ru.itis.delivery.models.enums.CartState;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository
@Slf4j
public class CustomCartRepositoryImpl implements CustomCartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Integer getCountOfProductsInCartByClient(Client client) {

        try{
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
            Root<CartItem> root = query.from(CartItem.class);
            query.select(criteriaBuilder.sum(root.get("count")))
                    .where(criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("cart").get("client"), client),
                            criteriaBuilder.equal(root.get("cart").get("cartState"), CartState.ACTIVE)
                    ));

            return entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            log.info("Ошибка при поиске количества товаров в корзине", e);
            throw new IllegalArgumentException(e);
        }

    }
}
