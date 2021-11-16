package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.query.QueryProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final QueryProcessor queryProcessor;

    public QueryServlet() {
        queryProcessor = new QueryProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        response.getWriter().println(queryProcessor.getQuery(command).execute());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}