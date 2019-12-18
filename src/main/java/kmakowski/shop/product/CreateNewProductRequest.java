package kmakowski.shop.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateNewProductRequest {
    private final String name;
    private final long priceCents;

    @JsonCreator
    public CreateNewProductRequest(
            @JsonProperty("name") String name,
            @JsonProperty("priceCents") long priceCents) {
        this.name = name;
        this.priceCents = priceCents;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public String getName() {
        return name;
    }
}
