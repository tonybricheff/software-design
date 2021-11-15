package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.servlet.DatabaseUtils.add;
import static ru.akirakozov.sd.refactoring.servlet.HtmlUtils.wrapResult;

public class QueryServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private QueryServlet servlet;

    @Before
    public void init() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        servlet = new QueryServlet();
        DatabaseUtils.clear();
    }

    @After
    public void after() throws SQLException {
        DatabaseUtils.clear();
    }

    @Test
    public void maxInEmptyTableTest() throws Exception {
        executeCommandTest("max", "<h1>Product with max price: </h1>\n");
    }

    @Test
    public void minInEmptyTableTest() throws Exception {
        executeCommandTest("min", "<h1>Product with min price: </h1>\n");
    }

    @Test
    public void sumInEmptyTableTest() throws Exception {
        executeCommandTest("sum", "Summary price: \n0\n");
    }

    @Test
    public void countInEmptyTableTest() throws Exception {
        executeCommandTest("count", "Number of products: \n0\n");
    }

    private void executeCommandTest(String command, String expected) throws Exception {
        when(request.getParameter("command")).thenReturn(command);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult(expected), result);
    }

    @Test
    public void maxFromMultipleProductsTest() throws Exception {
        executeCommandTestOnMultipleProducts(
                "max",
                "<h1>Product with max price: </h1>\nproduct100\t100</br>\n",
                100
        );
    }

    @Test
    public void minFromMultipleProductsTest() throws Exception {
        executeCommandTestOnMultipleProducts(
                "min",
                "<h1>Product with min price: </h1>\nproduct1\t1</br>\n",
                100
        );
    }

    @Test
    public void sumFromMultipleProductsTest() throws Exception {
        executeCommandTestOnMultipleProducts(
                "sum",
                "Summary price: \n5050\n",
                100
        );
    }

    @Test
    public void countFromMultipleProductsTest() throws Exception {
        executeCommandTestOnMultipleProducts(
                "count",
                "Number of products: \n100\n",
                100
        );
    }

    public void executeCommandTestOnMultipleProducts(String command, String expected, Integer numberOfProducts)
            throws Exception {
        add(numberOfProducts);
        when(request.getParameter("command")).thenReturn(command);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();

        Assert.assertEquals(wrapResult(expected), result);
    }
}
