package kmakowski.shop.product;

import kmakowski.shop.ShopApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(
        properties = "spring.main.allow-bean-definition-overriding=true",
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ShopApplication.class, ProductControllerIntegrationTest.MockedMongoConfig.class})
class ProductControllerIntegrationTest {
    private RestTemplate restTemplate = new RestTemplate();

    @Configuration
    static class MockedMongoConfig {
        @Bean
        @Primary
        ProductRepository productRepository() {
            return Mockito.mock(ProductRepository.class);
        }
    }

    @LocalServerPort
    private int port;

    @Test
    void shouldHandleIllegalArgumentExceptions() {
        Map<String, Object> req = new HashMap<>();
        req.put("name", "someNewProductName");
        req.put("priceCents", 0);

        String url = "http://localhost:" + port + "/products";

        assertThatThrownBy(() -> restTemplate.postForEntity(url, req, String.class))
                .isInstanceOf(HttpClientErrorException.class)
                .hasMessage("400 : [Price should be greater than 0 cents]");
    }
}
