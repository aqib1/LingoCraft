package com.lingo.craft.domain.user.ports.outbound;

import com.lingo.craft.domain.user.model.UserModel;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<UserModel> create(UserModel model);
    Optional<UserModel> getById(UUID id);
}
