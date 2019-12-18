package kmakowski.shop.product.mongo;

import com.mongodb.client.MongoClients;
import kmakowski.shop.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.testcontainers.containers.GenericContainer;

import java.util.List;

import static kmakowski.shop.product.ProductTestUtils.nextRandomProduct;
import static org.assertj.core.api.Assertions.assertThat;

class MongoProductRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(MongoProductRepositoryTest.class);
    private MongoProductRepository mongoProductRepository;
    private GenericContainer mongoContainer = new GenericContainer<>("mongo:4.0.6")
            .withExposedPorts(27017);

    @BeforeEach
    void setUp() {
        mongoContainer.start();
        String connectionString = "mongodb://" + mongoContainer.getContainerIpAddress() + ":" + mongoContainer.getFirstMappedPort();
        log.info("Will use connectionString: " + connectionString);
        MongoTemplate mongoOperations = new MongoTemplate(MongoClients.create(connectionString), "test");
        MongoRepositoryFactory mongoRepositoryFactory = new MongoRepositoryFactory(mongoOperations);
        mongoProductRepository = new MongoProductRepository(mongoRepositoryFactory.getRepository(SpringProductRepository.class));
    }

    @AfterEach
    void tearDown() {
        mongoContainer.close();
    }

    @Test
    void shouldListSavedProduct() {
        //given
        Product product = nextRandomProduct();

        //when
        mongoProductRepository.save(product);
        List<Product> list = mongoProductRepository.list(0, 100);

        //then
        assertThat(list).contains(product);
    }

    @Test
    void shouldFilterOutSoftlyDeletedProducts() {
        //given
        Product product = nextRandomProduct().withDeleted(true);

        //when
        mongoProductRepository.save(product);
        List<Product> list = mongoProductRepository.list(0, 100);

        //then
        assertThat(list).doesNotContain(product);
    }
}