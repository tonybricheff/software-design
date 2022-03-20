package com.brichev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

@Data
@AllArgsConstructor
public class Product implements DocumentedModel {
    private String name;
    private Double price;
    private Currency currency;

    public Product(Document document) {
        this(
                document.getString("name"),
                document.getDouble("price"),
                Currency.valueOf(document.getString("currency"))
        );
    }

    @Override
    public Document toDocument() {
        return new Document("name", name).append("price", price).append("currency", currency.toString());
    }






}
