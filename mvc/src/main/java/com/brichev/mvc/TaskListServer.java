package com.brichev.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskListServer {

    public static void main(String[] args) {
        new SpringApplication(TaskListServer.class).run(args);
    }
}
