package com.lingo.craft.domain.user.service;

import com.lingo.craft.domain.user.exception.InvalidUserIdException;
import com.lingo.craft.domain.user.exception.UserCreationException;
import com.lingo.craft.domain.user.exception.UserDeletionException;
import com.lingo.craft.domain.user.exception.UserNotFoundException;
import com.lingo.craft.domain.user.model.UserModel;
import com.lingo.craft.domain.user.ports.outbound.UserRepository;

import java.util.UUID;

import org.jooq.exception.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserModel getUserById(String id) {
        try {
            var uuid = UUID.fromString(id);
            var optionalUser = userRepository.getById(uuid);

            if (optionalUser.isEmpty()) {
                throw new UserNotFoundException(
                        HttpStatus.NOT_FOUND,
                        String.format(
                                "User not found against id = {%s}",
                                id
                        )
                );
            }
            return optionalUser.get();
        } catch (Exception ex) {
            throw new InvalidUserIdException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "User id {%s} is not in UUID format",
                            id
                    ),
                    ex
            );
        }
    }

    public UserModel deleteById(String id) {
        try {
            var uuid = UUID.fromString(id);

            var existingUser = getUserById(id);

            try {
                userRepository.deleteById(uuid);
            } catch (DataAccessException exception) {
                throw new UserDeletionException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        String.format(
                                "User deletion failed against id = {%s}",
                                id
                        ),
                        exception
                );
            }

            return existingUser;
        } catch (IllegalArgumentException ex) {
            throw new InvalidUserIdException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "User id {%s} is not in UUID format",
                            id
                    ),
                    ex
            );
        }
    }

    public UserModel getUserByEmailPassword(String email, String password) {
        try {

            var optionalUser = userRepository.getByEmailPassword(email, password);

            if (optionalUser.isEmpty()) {
                throw new UserNotFoundException(
                        HttpStatus.NOT_FOUND,
                        String.format(
                                "User not found against email = {%s}",
                                email
                        )
                );
            }

            return optionalUser.get();

        } catch (IllegalArgumentException ex) {
            throw new InvalidUserIdException(
                    HttpStatus.BAD_REQUEST,
                    String.format(
                            "Email id {%s} is not Valid",
                            email
                    ),
                    ex
            );
        }
    }

    public UserModel createUser(UserModel user) {
        var optionalUser = userRepository.create(user);

        if (optionalUser.isEmpty()) {
            throw new UserCreationException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error while creating user in database"
            );
        }
        return optionalUser.get();
    }
}
