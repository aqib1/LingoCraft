package com.lingo.craft.domain.user.service;

import com.lingo.craft.domain.user.exception.UserCreationException;
import com.lingo.craft.domain.user.model.UserModel;
import com.lingo.craft.domain.user.ports.outbound.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public UserModel createUser(UserModel user) {
        var optionalUser = userRepository.create(user);

        if(optionalUser.isEmpty()) {
            throw new UserCreationException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error while creating user in database"
            );
        }
        return optionalUser.get();
    }
}
