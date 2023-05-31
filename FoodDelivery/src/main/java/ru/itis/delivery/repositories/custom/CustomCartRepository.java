package ru.itis.delivery.repositories.custom;

import ru.itis.delivery.models.Client;

public interface CustomCartRepository {

    Integer getCountOfProductsInCartByClient(Client client);

}
