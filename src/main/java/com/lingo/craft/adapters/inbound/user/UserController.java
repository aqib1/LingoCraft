package com.lingo.craft.adapters.inbound.user;

import com.lingo.craft.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.diabolocom.release.openapi.model.CreateUserRequest;
import com.diabolocom.release.openapi.model.CreateUserResponse;
import com.diabolocom.release.openapi.model.GetUserResponse;

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

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUserById(
        @PathVariable String id
    ) {
        return ResponseEntity.ok(
            userModelMapper.toGetUserResponse(
                userService.getUserById(id)
            )
        );
    }
    @GetMapping("/email/{email}/password/{password}")
    public ResponseEntity<GetUserResponse> getByEmailPassword(
            @PathVariable String email,
            @PathVariable String password
    ) {
        return ResponseEntity.ok(
                userModelMapper.toGetUserResponse(
                        userService.getUserByEmailPassword(email, password)
                )
        );
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
        @RequestBody CreateUserRequest request
    ) {
        return ResponseEntity.ok(
            userModelMapper.toCreateUserResponse(
                userService.createUser(
                    userModelMapper.toUserModel(request)
                )
            )
        );
    }
}
