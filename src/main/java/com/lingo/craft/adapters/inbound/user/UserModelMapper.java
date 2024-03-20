package com.lingo.craft.adapters.inbound.user;

import com.lingo.craft.domain.user.model.UserModel;
import com.diabolocom.release.openapi.model.CreateUserRequest;
import com.diabolocom.release.openapi.model.CreateUserResponse;
import com.diabolocom.release.openapi.model.GetUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserModelMapper {

    @Mapping(target = "gender", expression = "java(getGenderValue(request))")
    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    UserModel toUserModel(CreateUserRequest request);

    @Mapping(target = "id", expression = "java(getStringIdFromUUID(model))")
    CreateUserResponse toCreateUserResponse(UserModel model);

    @Mapping(target = "id", expression = "java(getStringIdFromUUID(model))")
    GetUserResponse toGetUserResponse(UserModel model);

    @Mapping(target = "id", expression = "java(getStringIdFromUUID(model))")
    GetUserResponse toDeleteUserByIdResponse(UserModel model);


    default String getGenderValue(CreateUserRequest request) {
        return request.getGender().name();
    }

    default String getStringIdFromUUID(UserModel model) {
        return model.getId().toString();
    }
}
