package kmakowski.shop.product;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> listProducts(Pageable pageable) {
        return productService.list(pageable.getPageNumber(), pageable.getPageSize());
    }
    
    @PostMapping
    public Product create(@RequestBody CreateNewProductRequest request) {
        return productService.create(request);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        var deleteResult = productService.delete(id);
        switch (deleteResult) {
            case DELETED: return ResponseEntity.noContent().build();
            case NOT_FOUND: return ResponseEntity.notFound().build();
            default: throw new IllegalStateException("Unknown delete result " + deleteResult);
        }
    }
    
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity handleException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
