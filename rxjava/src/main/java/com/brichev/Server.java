package com.brichev;

import com.brichev.db.MongoReactiveDriver;
import com.brichev.service.ShopService;
import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class Server {
    private static final MongoReactiveDriver driver = new MongoReactiveDriver("mongodb://localhost:27017");
    private static final ShopService shopService = new ShopService(driver);

    public static void main(String[] args) {
        HttpServer
                .newServer(8080)
                .start(
                        (request, response) -> {
                            var action = request.getDecodedPath().substring(request.getDecodedPath().lastIndexOf("/") + 1);
                            var responseMessage = Observable.just("");
                            responseMessage = handleMapping(action, request.getQueryParameters());
                            return response.writeString(responseMessage);
                        }
                )
                .awaitShutdown();
    }

    private static Observable<String> handleMapping(String action, Map<String, List<String>> parameters) {
        switch (action) {
            case "sign-up":
                return shopService.addUser(parameters);
            case "add-product":
                return shopService.addProduct(parameters);
            case "product":
                return shopService.getProducts(parameters);
            default:
                throw new UnsupportedOperationException("Invalid action");
        }
    }
}
