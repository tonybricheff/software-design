package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static ru.akirakozov.sd.refactoring.servlet.DatabaseUtils.add;
import static ru.akirakozov.sd.refactoring.servlet.DatabaseUtils.clear;
import static ru.akirakozov.sd.refactoring.servlet.HtmlUtils.buildResultString;
import static ru.akirakozov.sd.refactoring.servlet.HtmlUtils.wrapResult;


public class GetProductsServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private GetProductsServlet servlet;

    @Before
    public void init() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        servlet = new GetProductsServlet();
        clear();
    }

    @After
    public void after() throws SQLException {
        clear();
    }

    @Test
    public void getFromEmptyTableTest() throws IOException {
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals(wrapResult(""), result);
    }

    @Test
    public void getFromTableWithOneProductTest() throws IOException, SQLException {
        getFromTableWithProducts(1);
    }

    @Test
    public void getFromTableWithMultipleProductsTest() throws IOException, SQLException {
        getFromTableWithProducts(100);
    }

    private void getFromTableWithProducts(Integer numberOfProducts) throws IOException, SQLException {
        add(numberOfProducts);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servlet.doGet(request, response);
        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals(wrapResult(buildResultString(numberOfProducts)), result);
    }
}
