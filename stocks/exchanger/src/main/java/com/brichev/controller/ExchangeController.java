package com.brichev.controller;

import com.brichev.exception.ExchangeException;
import com.brichev.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExchangeController {
    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }


    @PostMapping("/companies/{companyName}")
    public ResponseEntity<?> addCompany(@PathVariable String companyName) {
        return new ResponseEntity<>(exchangeService.addCompany(companyName), HttpStatus.CREATED);
    }

    @PostMapping("/companies/{companyId}/stocks/{amount}")
    public ResponseEntity<?> addStocks(@PathVariable Integer companyId, @PathVariable Integer amount) throws ExchangeException {
        exchangeService.addStocks(companyId, amount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/companies/{companyId}/stocks/{delta}")
    public ResponseEntity<Double> updateStockPrice(@PathVariable Integer companyId, @PathVariable Double delta) throws ExchangeException {
        exchangeService.updateStockPrice(companyId, delta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/companies/{companyId}/stocks")
    public ResponseEntity<?> getStock(@PathVariable Integer companyId) throws ExchangeException {
        return new ResponseEntity<>(exchangeService.getStock(companyId), HttpStatus.OK);
    }

    @GetMapping("/companies/{companyId}/stocks/{amount}/purchase")
    public ResponseEntity<Integer> buyStock(@PathVariable Integer companyId, @PathVariable Integer amount) throws ExchangeException {
        exchangeService.buyStock(companyId, amount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/companies/{companyId}/stocks/{amount}/sell")
    public ResponseEntity<?> sellStock(@PathVariable Integer companyId, @PathVariable Integer amount) throws ExchangeException {
        exchangeService.sellStock(companyId, amount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
