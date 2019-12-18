package kmakowski.shop.product.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SpringProductRepository extends PagingAndSortingRepository<MongoStoredProduct, String> {
    Page<MongoStoredProduct> findByDeleted(boolean deleted, Pageable pageable);
}
