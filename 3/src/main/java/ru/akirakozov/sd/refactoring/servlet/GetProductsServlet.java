package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.query.GetProductsQuery;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println(new GetProductsQuery().execute());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}