package ru.akirakozov.sd.refactoring.query;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddProductQuery extends BaseQuery {
    private final String name;
    private final int price;

    @Override
    public String execute() {
        try {
            db.updateQuery("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
