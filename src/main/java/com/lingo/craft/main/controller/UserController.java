package com.lingo.craft.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.diabolocom.release.openapi.model.CreateUserRequest;
import com.diabolocom.release.openapi.model.CreateUserResponse;

@RestController
public class UserController {

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(null);
    }
}
