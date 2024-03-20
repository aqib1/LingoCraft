package com.lingo.craft.adapters.outbound.user;

import com.lingo.craft.domain.user.model.UserModel;
import com.lingo.craft.domain.user.ports.outbound.UserRepository;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
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

    @Override
    public Optional<UserModel> getById(UUID id) {
        var userRecord = dslContext.selectFrom(USER)
            .where(USER.ID.eq(id))
            .fetchOne();

        return Objects.isNull(userRecord) ?
            Optional.empty() :
            Optional.of(
                userRecordMapper.toUserModel(userRecord)
            );
    }

    @Override
    public  Optional<UserModel> deleteById(UUID id){
        var userRecord = dslContext.selectFrom(USER)
                .where(USER.ID.eq(id))
                .fetchOne();

        if (Objects.isNull(userRecord)){
            return Optional.empty();
        } else{
            dslContext.deleteFrom(USER)
                    .where(USER.ID.eq(id))
                    .execute();

            return Optional.of(userRecordMapper.toUserModel(userRecord));
        }
    }


    @Override
    public Optional<UserModel> getByEmailPassword(String email, String password) {
        var userRecord = dslContext.selectFrom(USER)
                .where(USER.EMAIL.eq(email),USER.PASSWORD.eq(password))
                .fetchOne();

        return Objects.isNull(userRecord) ?
                Optional.empty() :
                Optional.of(
                        userRecordMapper.toUserModel(userRecord)
                );
    }
}
