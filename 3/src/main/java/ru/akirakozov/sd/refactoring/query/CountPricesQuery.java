package ru.akirakozov.sd.refactoring.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountPricesQuery extends BaseQuery {

    @Override
    public String execute() {
        builder.add("Number of products: ");
        try {
            executeQuery("SELECT COUNT(*) FROM PRODUCT");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.toString();
    }

    @Override
    protected void executeQuery(String query) throws SQLException {
        db.executeQuery(builder, query, (ResultSet rs) -> {
            ArrayList<String> list = new ArrayList<>();
            try {
                if (rs.next()) {
                    list.add(Integer.toString(rs.getInt(1)));
                }
            } catch (SQLException ignore) {
            }
            return list;
        });
    }
}
