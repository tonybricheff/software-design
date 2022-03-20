package com.brichev.service;

import com.brichev.db.MongoReactiveDriver;
import com.brichev.model.Currency;
import com.brichev.model.Product;
import com.brichev.model.User;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class ShopService {
    private final MongoReactiveDriver driver;

    public ShopService(MongoReactiveDriver driver) {
        this.driver = driver;
    }

    public Observable<String> addUser(Map<String, List<String>> parameters) {
        var id = Integer.parseInt(parameters.get("id").get(0));
        var currency = Currency.valueOf(parameters.get("currency").get(0));
        return driver.addUser(new User(id, currency))
                .map(o -> "User " + id + " inserted");
    }

    public Observable<String> addProduct(Map<String, List<String>> parameters) {
        var name = parameters.get("name").get(0);
        var value = Double.parseDouble(parameters.get("value").get(0));
        var currency = Currency.valueOf(parameters.get("currency").get(0));
        return driver.addProduct(new Product(name, value, currency))
                .map(o -> "Product " + name + " inserted");
    }

    public Observable<String> getProducts(Map<String, List<String>> parameters) {
        var id = Integer.parseInt(parameters.get("id").get(0));
        return driver.getUserById(id)
                .map(User::getCurrency)
                .flatMap(currency -> driver.getAllProducts().map(o -> {
                            var stringBuilder = new StringBuilder();
                            o.forEach(product ->
                                    stringBuilder.append("Product ")
                                            .append(product.getName())
                                            .append(" ")
                                            .append(product.getCurrency().convert(currency, product.getPrice()))
                                            .append(" ")
                                            .append(currency)
                                            .append('\n'));
                            return stringBuilder.toString();
                        }
                ));
    }
}
