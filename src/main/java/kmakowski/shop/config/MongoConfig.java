package kmakowski.shop.config;

import kmakowski.shop.product.ProductRepository;
import kmakowski.shop.product.mongo.MongoProductRepository;
import kmakowski.shop.product.mongo.SpringProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = SpringProductRepository.class)
public class MongoConfig {
    
    @Bean
    ProductRepository productRepository(SpringProductRepository springProductRepository) {
        return new MongoProductRepository(springProductRepository);
    }
}
