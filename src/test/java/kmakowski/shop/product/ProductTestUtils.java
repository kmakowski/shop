package kmakowski.shop.product;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class ProductTestUtils {

    public static Product nextRandomProduct() {
        String id = UUID.randomUUID().toString();
        long price = new Random().nextInt(1000);
        return new Product(id, "some name " + id, price, Instant.now().getEpochSecond(), false);
    }
}
