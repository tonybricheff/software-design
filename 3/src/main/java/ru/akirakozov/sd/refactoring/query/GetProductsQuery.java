package ru.akirakozov.sd.refactoring.query;

public class GetProductsQuery extends BaseQuery {

    @Override
    public String execute() {
        try {
            executeQuery("SELECT * FROM PRODUCT");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }
}

