package com.brichev.client;

import com.brichev.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.brichev.exception.ErrorCodes.REQUEST_ERROR;

@Service
public class RestClientService {

    private final RestTemplate restTemplate;

    @Autowired
    public RestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> sendRequest(String baseUrl, HttpMethod httpMethod, Class<?> objectClass) throws UserException {
        try {
            return restTemplate.exchange(
                    baseUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(null),
                    objectClass
            );
        } catch (Exception e) {
            throw new UserException(REQUEST_ERROR);
        }
    }
}
