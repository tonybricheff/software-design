package com.brichev.db;

import com.brichev.model.Product;
import com.brichev.model.User;
import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class MongoReactiveDriver {
    private final MongoClient client;
    private final String DATABASE_NAME = "rxjava_db";
    private final String USER_COLLECTION = "customers";
    private final String PRODUCT_COLLECTION = "products";

    public MongoReactiveDriver(String url) {
        client = MongoClients.create(url);
    }

    public Observable<Success> addUser(User user) {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(USER_COLLECTION)
                .insertOne(user.toDocument());
    }

    public Observable<Success> addProduct(Product product) {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(PRODUCT_COLLECTION)
                .insertOne(product.toDocument());
    }

    public Observable<User> getUserById(Integer id) {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(USER_COLLECTION)
                .find(Filters.eq("id", id))
                .toObservable()
                .map(User::new);
    }

    public Observable<List<Product>> getAllProducts() {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(PRODUCT_COLLECTION)
                .find()
                .toObservable()
                .toList()
                .map(documents -> {
                            var products = new ArrayList<Product>();
                            documents.forEach(document -> products.add(new Product(document)));
                            return products;
                        }
                );
    }
}
