package com.brichev;

import com.brichev.service.ExchangeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class ExchangeServer {
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(ExchangeServer.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
        app.run(args);
    }
}
