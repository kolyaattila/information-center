package com.information.center.accountservice.client;

import com.information.center.accountservice.model.CreateAccountRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "auth-service", fallback = AuthServiceClientFallBack.class)
public interface AuthServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/auth/user", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<?> createUser(@RequestBody CreateAccountRequest user);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{username}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<?> deleteUser(@PathVariable("username") String username);
}




