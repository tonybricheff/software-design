package com.brichev.controller;

import com.brichev.exception.UserException;
import com.brichev.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(String name) {
        userService.addUser(name);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}/balance/{amount}")
    public ResponseEntity<String> addMoney(@PathVariable Integer userId, @PathVariable Double amount) throws UserException {
        userService.updateBalance(userId, amount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId}/stocks")
    public ResponseEntity<?> getUserStocks(@PathVariable Integer userId) throws UserException {
        return new ResponseEntity<>(userService.getUserStocks(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<?> getUserBalance(@PathVariable Integer userId) throws UserException {
        return new ResponseEntity<>(userService.getUserBalance(userId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/companies/{companyId}/stocks/{amount}/purchase")
    public ResponseEntity<?> buyStock(@PathVariable Integer userId, @PathVariable Integer companyId, @PathVariable Integer amount) throws UserException {
        userService.buyStock(userId, companyId, amount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/users/{userId}/companies/{companyId}/stocks/{amount}/sell")
    public ResponseEntity<?> sellStock(@PathVariable Integer userId, @PathVariable Integer companyId, @PathVariable Integer amount) throws UserException {
        userService.sellStock(userId, companyId, amount);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
