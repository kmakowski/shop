package kmakowski.shop.product.mongo;

import kmakowski.shop.product.Product;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
public class MongoStoredProduct {
    @Id
    private String id;
    private String name;
    private long priceCents;
    private long createdAtEpochSeconds;
    private boolean deleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public void setPriceCents(long priceCents) {
        this.priceCents = priceCents;
    }

    public long getCreatedAtEpochSeconds() {
        return createdAtEpochSeconds;
    }

    public void setCreatedAtEpochSeconds(long createdAtEpochSeconds) {
        this.createdAtEpochSeconds = createdAtEpochSeconds;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public static MongoStoredProduct from(Product product) {
        MongoStoredProduct result = new MongoStoredProduct();
        result.setId(product.getId());
        result.setCreatedAtEpochSeconds(product.getCreatedAtEpochSeconds());
        result.setName(product.getName());
        result.setPriceCents(product.getPriceCents());
        result.setDeleted(product.isDeleted());
        return result;
    }
    
    public Product asProductDto() {
        return new Product(id, name, priceCents, createdAtEpochSeconds, deleted);
    }
}
