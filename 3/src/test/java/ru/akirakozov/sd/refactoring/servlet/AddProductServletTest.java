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

public class AddProductServletTest {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private AddProductServlet servlet;

    @Before
    public void init() throws SQLException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        stringWriter = new StringWriter();
        servlet = new AddProductServlet();
        DatabaseUtils.clear();
    }

    @After
    public void after() throws SQLException {
        DatabaseUtils.clear();
    }

    @Test
    public void simpleNameAndPriceTest() throws Exception {
        executeNameTest("name");
    }

    @Test
    public void nullNameAndPriceTest() throws Exception {
        executeNameTest(null);
    }

    @Test
    public void emptyNameAndPriceTest() throws Exception {
        executeNameTest("");
    }

    private void executeNameTest(String name) throws IOException {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("price")).thenReturn("100");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));

        servlet.doGet(request, response);

        String result = stringWriter.getBuffer().toString();
        Assert.assertEquals("OK\n", result);
    }


    @Test(expected = NumberFormatException.class)
    public void nameAndNullPriceTest() throws IOException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn(null);
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servlet.doGet(request, response);
    }

    @Test(expected = NumberFormatException.class)
    public void nameAndEmptyPriceTest() throws IOException {
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("price")).thenReturn("price");
        when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
        servlet.doGet(request, response);
    }
}
