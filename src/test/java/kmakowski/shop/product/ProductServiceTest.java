package kmakowski.shop.product;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static kmakowski.shop.product.ProductTestUtils.nextRandomProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSaveCreatedProduct() {
        //given
        CreateNewProductRequest req = new CreateNewProductRequest("someProduct", 203);
        
        //when
        Product product = productService.create(req);

        //then
        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo(req.getName());
        assertThat(product.getPriceCents()).isEqualTo(req.getPriceCents());
        assertThat(product.getId()).isNotEmpty();
        assertThat(product.getCreatedAtEpochSeconds()).isCloseTo(Instant.now().getEpochSecond(), Offset.offset(10L));
        
        verify(productRepository).save(product);
    }

    @Test
    void shouldListSavedProducts() {
        //given
        List<Product> products = asList(nextRandomProduct(), nextRandomProduct());
        
        given(productRepository.list(0, 100)).willReturn(products);
        
        //when
        List<Product> result = productService.list(0, 100);
        
        //then
        assertThat(result).containsExactlyElementsOf(products);
    }

    @Test
    void shouldSoftlyDeleteProduct() {
        //given
        Product product = nextRandomProduct();
        given(productRepository.find(product.getId())).willReturn(Optional.of(product));

        //when
        DeleteResult deleteResult = productService.delete(product.getId());
        
        //then
        assertThat(deleteResult).isEqualTo(DeleteResult.DELETED);
        verify(productRepository).save(argThat(p -> p.isDeleted() && p.getId().equals(product.getId())));
    }
    
    @Test
    void shouldFailWhenDeletingNonExistingProduct() {
        //given
        String id = "someId";
        given(productRepository.find(id)).willReturn(Optional.empty());

        //when
        DeleteResult deleteResult = productService.delete(id);
        
        //then
        assertThat(deleteResult).isEqualTo(DeleteResult.NOT_FOUND);
    }    
    
    @Test
    void shouldValidateProductCreationRequest() {
        //given
        var req = new CreateNewProductRequest("", 12);
        
        //when
        assertThatThrownBy(() -> productService.create(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product Name is required");
    }
}