package com.lingo.craft.adapters.inbound.user;

import com.lingo.craft.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diabolocom.release.openapi.model.CreateUserRequest;
import com.diabolocom.release.openapi.model.CreateUserResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserModelMapper userModelMapper;

    public UserController(
        UserService userService,
        UserModelMapper userModelMapper
    ) {
        this.userService = userService;
        this.userModelMapper = userModelMapper;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.ok(
            userModelMapper.toCreateUserResponse(
                userService.createUser(
                    userModelMapper.toUserModel(request)
                )));
    }
}
