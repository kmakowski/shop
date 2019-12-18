package kmakowski.shop.product.mongo;

import kmakowski.shop.product.Product;
import kmakowski.shop.product.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MongoProductRepository implements ProductRepository {
    private final SpringProductRepository springProductRepository;

    public MongoProductRepository(SpringProductRepository springProductRepository) {
        this.springProductRepository = springProductRepository;
    }

    @Override
    public void save(Product product) {
        springProductRepository.save(MongoStoredProduct.from(product));
    }

    @Override
    public List<Product> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        
        return springProductRepository.findByDeleted(false, pageable).get()
                .map(MongoStoredProduct::asProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> find(String id) {
        return springProductRepository.findById(id).map(MongoStoredProduct::asProductDto);
    }
}
