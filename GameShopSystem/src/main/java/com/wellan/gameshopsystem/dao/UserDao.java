package com.wellan.gameshopsystem.dao;

import com.wellan.gameshopsystem.dto.UserRequest;
import com.wellan.gameshopsystem.model.User;

public interface UserDao {

    Integer createUser(UserRequest userRequest);

    User getUserById(Integer userId);

    User getUserByEmail(String email);
}
