package com.brichev.service;

import com.brichev.exception.ExchangeException;
import com.brichev.model.Company;
import com.brichev.model.Stock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.brichev.exception.ErrorCodes.INVALID_PARAMETER;
import static com.brichev.exception.ErrorCodes.STOCK_NOT_FOUND;

@Service
public class ExchangeService {
    private final List<Stock> stocks = new ArrayList<>();

    public Company addCompany(String name) {
        int id = stocks.size();
        stocks.add(new Stock(id, name, 0, 0));
        return new Company(id, name);
    }

    public void addStocks(int companyId, int amount) throws ExchangeException {
        if (companyId >= stocks.size() || amount <= 0) {
            throw new ExchangeException(INVALID_PARAMETER);
        }
        stocks.get(companyId).updateAmount(amount);
    }

    public void updateStockPrice(int companyId, double delta) throws ExchangeException {
        if (companyId >= stocks.size()) {
            throw new ExchangeException(INVALID_PARAMETER);
        }
        stocks.get(companyId).updatePrice(delta);
    }

    public Stock getStock(int companyId) throws ExchangeException {
        if (companyId >= stocks.size()) {
            throw new ExchangeException(STOCK_NOT_FOUND);
        }
        return stocks.get(companyId);
    }

    public void buyStock(int companyId, int amount) throws ExchangeException {
        if (companyId >= stocks.size() || amount <= 0) {
            throw new ExchangeException(INVALID_PARAMETER);
        }
        stocks.get(companyId).updateAmount(amount);
    }

    public void sellStock(int companyId, int amount) throws ExchangeException {
        if (companyId >= stocks.size() || amount <= 0) {
            throw new ExchangeException(INVALID_PARAMETER);
        }
        Stock stock = stocks.get(companyId);
        stock.updateAmount(amount);
    }
}
