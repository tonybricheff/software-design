package ru.akirakozov.sd.refactoring.query;

import ru.akirakozov.sd.refactoring.db.Database;
import ru.akirakozov.sd.refactoring.html.HtmlBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class BaseQuery implements Query {
    protected Database db;
    protected HtmlBuilder builder;

    BaseQuery() {
        db = new Database("jdbc:sqlite:test.db");
        builder = new HtmlBuilder();
    }

    protected void executeQuery(String query) throws SQLException {
        db.executeQuery(builder, query, (ResultSet rs) -> {
            ArrayList<String> list = new ArrayList<>();
            try {
                while (rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    list.add(name + "\t" + price + "</br>");
                }
            } catch (SQLException ignore) {
            }
            return list;
        });
    }
}