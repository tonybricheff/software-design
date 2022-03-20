package com.brichev.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.Document;

@Data
@AllArgsConstructor
public class User implements DocumentedModel{
    private Integer id;
    private Currency currency;

    public User(Document document) {
        this(document.getInteger("id"), Currency.valueOf(document.getString("currency")));
    }

    @Override
    public Document toDocument() {
        return new Document("id", id)
                .append("currency", currency.toString());
    }
}
