package ru.akirakozov.sd.refactoring.query;

public class MinPriceQuery extends BaseQuery {

    @Override
    public String execute() {
        builder.add("<h1>Product with min price: </h1>");
        try {
            executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}
