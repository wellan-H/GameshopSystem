package com.wellan.gameshopsystem.service;

import com.wellan.gameshopsystem.dto.UserRequest;
import com.wellan.gameshopsystem.model.User;

public interface UserService {
    Integer register(UserRequest userRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    User login(UserRequest userRequest);
}
