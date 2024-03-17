package com.lingo.craft.adapters.outbound.user;

import com.lingo.craft.domain.user.model.UserModel;
import com.lingo.craft.domain.user.ports.outbound.UserRepository;
import java.util.Objects;
import java.util.Optional;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.lingo.craft.Tables.USER;

@Repository
public class JdbcUserRepository implements UserRepository {
    private final DSLContext dslContext;
    private final UserRecordMapper userRecordMapper;

    public JdbcUserRepository(
        DSLContext dslContext,
        UserRecordMapper userRecordMapper
    ) {
        this.dslContext = dslContext;
        this.userRecordMapper = userRecordMapper;
    }

    @Override
    @Transactional
    public Optional<UserModel> create(UserModel model) {
        var userRecord = dslContext.insertInto(USER)
            .set(userRecordMapper.toUserRecord(model))
            .returning()
            .fetchAny();
        return Objects.isNull(userRecord) ?
            Optional.empty() :
            Optional.of(
                userRecordMapper.toUserModel(userRecord)
            );
    }
}
