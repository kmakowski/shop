package kmakowski.shop.product;

import java.util.Objects;

public class Product {
    private final String id;
    private final String name;
    private final long priceCents;
    private final long createdAtEpochSeconds;
    private final boolean deleted;

    public Product(String id, String name, long priceCents, long createdAtEpochSeconds, boolean deleted) {
        this.id = id;
        this.name = name;
        this.priceCents = priceCents;
        this.createdAtEpochSeconds = createdAtEpochSeconds;
        this.deleted = deleted;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public long getCreatedAtEpochSeconds() {
        return createdAtEpochSeconds;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public Product withDeleted(boolean deleted) {
        return new Product(id, name, priceCents, createdAtEpochSeconds, deleted);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product that = (Product) o;
        return priceCents == that.priceCents &&
                createdAtEpochSeconds == that.createdAtEpochSeconds &&
                deleted == that.deleted &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, priceCents, createdAtEpochSeconds, deleted);
    }
}
