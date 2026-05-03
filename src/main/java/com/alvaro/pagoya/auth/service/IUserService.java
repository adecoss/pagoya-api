package com.alvaro.pagoya.auth.service;

import com.alvaro.pagoya.auth.dto.RegisterUserRequest;
import com.alvaro.pagoya.auth.dto.UserResponse;

public interface IUserService {
    UserResponse register(RegisterUserRequest request);
}
