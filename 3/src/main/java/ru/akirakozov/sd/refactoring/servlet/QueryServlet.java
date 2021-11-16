package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.html.HtmlBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        if ("max".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
                    HtmlBuilder builder = new HtmlBuilder();
                    builder.add("<h1>Product with max price: </h1>");
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        builder.add(name + "\t" + price + "</br>");
                    }
                    response.getWriter().println(builder.toString());

                    rs.close();
                    stmt.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
                    HtmlBuilder builder = new HtmlBuilder();
                    builder.add("<h1>Product with min price: </h1>");

                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        builder.add(name + "\t" + price + "</br>");
                    }
                    response.getWriter().println(builder.toString());

                    rs.close();
                    stmt.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SUM(price) FROM PRODUCT");
                    HtmlBuilder builder = new HtmlBuilder();
                    builder.add("Summary price: ");

                    if (rs.next()) {
                        builder.add(rs.getInt(1));
                    }
                    response.getWriter().println(builder.toString());

                    rs.close();
                    stmt.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {
                try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                    Statement stmt = c.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM PRODUCT");
                    HtmlBuilder builder = new HtmlBuilder();
                    builder.add("Number of products: ");

                    if (rs.next()) {
                        builder.add(rs.getInt(1));
                    }
                    response.getWriter().println(builder.toString());

                    rs.close();
                    stmt.close();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}