package com.lingo.craft.adapters.outbound.user;

import com.lingo.craft.domain.user.model.UserModel;
import com.lingo.craft.tables.records.UserRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserRecordMapper {
    @Mapping(target = "roleid", source = "roleId")
    UserRecord toUserRecord(UserModel userModel);

    @Mapping(target = "roleId", source = "roleid")
    UserModel toUserModel(UserRecord userRecord);

}
