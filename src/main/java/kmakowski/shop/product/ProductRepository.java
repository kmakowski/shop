package kmakowski.shop.product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    void save(Product product);
    List<Product> list(int page, int size);
    Optional<Product> find(String id);
}
