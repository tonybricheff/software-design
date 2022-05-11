package com.brichev.service;

import com.brichev.client.ExchangeServiceClient;
import com.brichev.exception.UserException;
import com.brichev.model.Stock;
import com.brichev.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.brichev.exception.ErrorCodes.*;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();
    private final ExchangeServiceClient client;

    @Autowired
    public UserService(ExchangeServiceClient client) {
        this.client = client;
    }

    public User addUser(String name) {
        int id = users.size();
        var user = new User(id, name, 0, new HashMap<>());
        users.add(user);
        return user;
    }

    public void updateBalance(int userId, double delta) throws UserException {
        if (userId > users.size() || delta <= 0) {
            throw new UserException(INVALID_PARAMETER);
        }
        users.get(userId).updateBalance(delta);
    }


    public List<Stock> getUserStocks(int userId) throws UserException {
        List<Stock> stocks = new ArrayList<>();
        if (userId < users.size()) {
            User user = users.get(userId);
            for (var stock : user.getStocks().entrySet()) {
                var companyId = stock.getKey();
                var countStocks = stock.getValue();
                stocks.add(new Stock(companyId, client.getStocks(companyId).getPrice(), countStocks));
            }
        }
        return stocks;
    }

    public Double getUserBalance(int userId) throws UserException {
        Double balance = null;
        if (userId < users.size()) {
            var stocks = getUserStocks(userId);
            balance = users.get(userId).getBalance();
            for (var s : stocks) {
                balance += s.getNumber() * s.getPrice();
            }
        }
        return balance;
    }

    public void buyStock(int userId, int companyId, int number) throws UserException {
        if (userId >= users.size()) {
            throw new UserException(INVALID_PARAMETER);
        }

        var user = users.get(userId);
        var totalCost = client.getStocks(companyId).getPrice() * number;
        if (user.getBalance() - totalCost < 0) {
            throw new UserException(NOT_ENOUGH_MONEY);
        }

        if (client.buyStocks(companyId, number)) {
            user.getStocks().put(companyId, user.getStocks().getOrDefault(companyId, 0) + number);
            user.updateBalance(-totalCost);
        } else {
            throw new UserException(EXCHANGE_FAILED);
        }
    }

    public void sellStock(int userId, int companyId, int number) throws UserException {
        if (userId >= users.size()) {
            throw new UserException(INVALID_PARAMETER);
        }
        var user = users.get(userId);
        var totalCost = client.getStocks(companyId).getPrice();
        if (user.getStocks().getOrDefault(companyId, 0) < number) {
            throw new UserException(NOT_ENOUGH_STOCKS);
        }
        if (client.sellStocks(companyId, number)) {
            user.getStocks().put(companyId, user.getStocks().getOrDefault(companyId, 0) - number);
            user.updateBalance(totalCost);
        } else {
            throw new UserException(EXCHANGE_FAILED);
        }
    }
}