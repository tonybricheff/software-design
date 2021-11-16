package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HtmlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            executeGetQuery(c, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void executeGetQuery(Connection c, HttpServletResponse response) throws SQLException, IOException {
        try (Statement stmt = c.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT");
            HtmlBuilder builder = new HtmlBuilder();
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                builder.add(name + "\t" + price + "</br>");
            }
            response.getWriter().println(builder.toString());
            rs.close();
        }
    }
}