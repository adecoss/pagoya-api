package com.alvaro.pagoya.auth.mapper;

import com.alvaro.pagoya.auth.dto.RegisterUserRequest;
import com.alvaro.pagoya.auth.dto.UserResponse;
import com.alvaro.pagoya.auth.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "verified", constant = "false")
    @Mapping(target = "role", ignore = true)
    User toEntity(RegisterUserRequest request);

    @Mapping(target = "role", source = "role.name")
    UserResponse toResponse(User user);
}
