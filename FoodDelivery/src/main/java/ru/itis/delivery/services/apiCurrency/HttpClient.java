package ru.itis.delivery.services.apiCurrency;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClient {

    private final OkHttpClient client = new OkHttpClient();

    public String sendGetRequest(String url, HeaderCurrency headerCurrency) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader(headerCurrency.name, headerCurrency.value)
                .build();

        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            return response.body().string();
        }
    }
}
