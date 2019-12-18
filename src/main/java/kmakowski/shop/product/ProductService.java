package kmakowski.shop.product;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static kmakowski.shop.product.DeleteResult.DELETED;
import static kmakowski.shop.product.DeleteResult.NOT_FOUND;

@Service
class ProductService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    Product create(CreateNewProductRequest req) {
        if (req.getPriceCents() < 1) {
            throw new IllegalArgumentException("Price should be greater than 0 cents");
        }

        if (req.getName() == null || req.getName().isBlank()) {
            throw new IllegalArgumentException("Product Name is required");
        }

        var createdAtEpochSeconds = Instant.now().getEpochSecond();
        var id = UUID.randomUUID().toString();
        var product = new Product(id, req.getName(), req.getPriceCents(), createdAtEpochSeconds, false);
        
        productRepository.save(product);
        
        return product;
    }
    
    List<Product> list(int page, int size) {
        return productRepository.list(page, size);
    }
    
    DeleteResult delete(String productId) {
        return productRepository.find(productId).map(p -> {
            
            productRepository.save(p.withDeleted(true));
            
            return DELETED;
        }).orElse(NOT_FOUND);
    }
}
