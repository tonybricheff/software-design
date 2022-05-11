package com.brichev.client;

import com.brichev.exception.UserException;
import com.brichev.model.Company;
import com.brichev.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class ExchangeServiceClient {
    private final RestClientService restClientService;
    private final String apiUrl = "http://127.0.0.1:8080";

    @Autowired
    public ExchangeServiceClient(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    public boolean addStocks(int companyId, int amount) throws UserException {
        var response = restClientService.sendRequest(
                apiUrl + "/companies/" + companyId + "/stocks/" + amount,
                HttpMethod.POST,
                Object.class
        );
        return response.getStatusCode().is2xxSuccessful();
    }

    public Company addCompany(String companyName) throws UserException {
        var response = restClientService.sendRequest(
                apiUrl + "/companies/" + companyName,
                HttpMethod.POST,
                Company.class
        );
        return (Company) response.getBody();
    }

    public Stock getStocks(int companyId) throws UserException {
        var response = restClientService.sendRequest(
                apiUrl + "/companies/" + companyId + "/stocks",
                HttpMethod.GET,
                Stock.class
        );
        return (Stock) response.getBody();
    }

    public boolean updateStockPrice(int companyId, double delta) throws UserException {
        var response = restClientService.sendRequest(
                apiUrl + "/companies/" + companyId + "/stocks/" + delta,
                HttpMethod.PUT,
                Object.class
        );
        return response.getStatusCode().is2xxSuccessful();
    }


    public boolean buyStocks(int companyId, int amount) throws UserException {
        var response = restClientService.sendRequest(
                apiUrl + "/companies/" + companyId + "/stocks/" + amount + "/purchase",
                HttpMethod.GET,
                Object.class
        );
        return response.getStatusCode().is2xxSuccessful();
    }

    public boolean sellStocks(int companyId, int amount) throws UserException {
        var response = restClientService.sendRequest(
                apiUrl + "/companies/" + companyId + "/stocks/" + amount + "/sell",
                HttpMethod.GET,
                Object.class
        );
        return response.getStatusCode().is2xxSuccessful();
    }
}
