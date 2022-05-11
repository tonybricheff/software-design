package com.brichev;

import com.brichev.client.ExchangeServiceClient;
import com.brichev.client.RestClientService;
import com.brichev.exception.UserException;
import com.brichev.model.Stock;
import com.brichev.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

import java.util.List;


public class UserExchangeTest {

    @ClassRule
    public static final GenericContainer<?> __ = new FixedHostPortGenericContainer<>("stocks:0.1.0-SNAPSHOT")
            .withFixedExposedPort(8080, 8080)
            .withExposedPorts(8080);


    private UserService userService;
    private ExchangeServiceClient client;

    @Before
    public void before() throws UserException {
        client = new ExchangeServiceClient(new RestClientService(new RestTemplate()));
        userService = new UserService(client);

        var id1 = client.addCompany("Tesla").getId();
        client.addStocks(id1, 100);
        client.updateStockPrice(id1, 100);

        var id2 = client.addCompany("Apple").getId();
        client.addStocks(id2, 1);
        client.updateStockPrice(id2, 10);

        var id3 = client.addCompany("Alibaba").getId();
        client.addStocks(id3, 5);
        client.updateStockPrice(id3, 1000);

        var id4 = client.addCompany("Meta platforms").getId();
        client.addStocks(id4, 1000);
        client.updateStockPrice(id4, 5);

    }

    @Test
    public void testAddStocksAndChangeCost() throws UserException {
        int id5 = client.addCompany("Test").getId();
        Assert.assertTrue(client.addStocks(id5, 1));
        Assert.assertTrue(client.updateStockPrice(id5, 1));
    }

    @Test
    public void testAddUser() {
        Assert.assertEquals(0, userService.addUser("User 1").getId());
        Assert.assertEquals(1, userService.addUser("User 2").getId());
    }

    @Test
    public void testBuyAndSellStock() throws UserException {
        Assert.assertEquals(0, userService.addUser("User 1").getId());

        Assertions.assertDoesNotThrow(() -> userService.updateBalance(0, 5));
        Assertions.assertThrows(UserException.class, () -> userService.buyStock(0, 1, 1));
        Assertions.assertDoesNotThrow(() -> userService.updateBalance(0, 15));

        Assertions.assertThrows(UserException.class, () -> userService.buyStock(0, 1, 0));
        Assertions.assertDoesNotThrow(() -> userService.buyStock(0, 1, 1));
        Assert.assertEquals(20, userService.getUserBalance(0), 0.0001);

        Assertions.assertDoesNotThrow(() -> userService.sellStock(0, 1, 1));
        Assert.assertEquals(20, userService.getUserBalance(0), 0.0001);
        Assertions.assertThrows(UserException.class, () -> userService.sellStock(0, 1, 1));
    }

    @Test
    public void testGetStocks() throws UserException {
        Assert.assertEquals(0, userService.addUser("User 1").getId());

        Assertions.assertDoesNotThrow(() -> userService.updateBalance(0, 100000));
        Assertions.assertDoesNotThrow(() -> userService.buyStock(0, 0, 10));
        Assertions.assertDoesNotThrow(() -> userService.buyStock(0, 2, 10));

        Assert.assertEquals(100000, userService.getUserBalance(0), 0.1);

        List<Stock> stocks = userService.getUserStocks(0);

        Assert.assertEquals(2, stocks.size());
        Assert.assertEquals(0, stocks.get(0).getId());
        Assert.assertEquals(2, stocks.get(1).getId());
        Assert.assertEquals(100, stocks.get(0).getPrice(), 0.1);
        Assert.assertEquals(1000, stocks.get(1).getPrice(), 0.1);
        Assert.assertEquals(10, stocks.get(0).getNumber());
        Assert.assertEquals(10, stocks.get(1).getNumber());
    }
}