package ru.itis.delivery.services.apiCurrency;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;


import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final String URL = "https://api.apilayer.com/exchangerates_data/latest?base=RUB&symbols=USD,EUR,GBP";
    private Map<String, Double> exchangeRates;
    private final HttpClient httpClient;

    private final ObjectMapper objectMapper;

    public Map<String, Double> getRates() {
        if (exchangeRates == null) {
            // Выполняется только при первом вызове метода
            exchangeRates = getRatesFromAPI();
        }
        return exchangeRates;
    }

    private Map<String, Double> getRatesFromAPI() {
        try {
            HeaderCurrency headerCurrency = HeaderCurrency.builder()
                    .name("apikey").value("ZW5Y9DqbTLm2GRBEfFvScA07TdnSQflu").build();

            String response = httpClient.sendGetRequest(URL, headerCurrency); // Отправляем GET-запрос на API для получения данных о курсах валют
            Map<String, Object> exchangeRatesData = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {}); // Используем TypeReference для преобразования JSON в Map<String, Object>

            return objectMapper.convertValue(exchangeRatesData.get("rates"), new TypeReference<Map<String, Double>>() {});
//            return (Map<String, Double>) exchangeRatesData.get("rates"); // Приводим значение к типу Map<String, Double>
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Double convertCurrency(double price, String currency) {
        if (getRates().containsKey(currency)) {
            double rate = exchangeRates.get(currency); // Получаем курс валюты для целевой валюты из сохраненных данных
            return price * rate; // Выполняем пересчёт стоимости
        } else {
            throw new IllegalArgumentException("Invalid target currency");
        }
    }

}
