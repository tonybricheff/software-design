package ru.akirakozov.sd.refactoring.query;

public class MaxPriceQuery extends BaseQuery {

    @Override
    public String execute() {
        builder.add("<h1>Product with max price: </h1>");
        try {
            executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
