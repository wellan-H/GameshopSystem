package com.wellan.gameshopsystem.service.impl;

import com.wellan.gameshopsystem.dao.UserDao;
import com.wellan.gameshopsystem.dto.UserRequest;
import com.wellan.gameshopsystem.model.User;
import com.wellan.gameshopsystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Override
    public Integer register(UserRequest userRequest) {
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if (user!=null){
            log.warn("該email{}已被註冊",userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //hash加密
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        userRequest.setPassword(hashedPassword);
        return userDao.createUser(userRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User login(UserRequest userRequest) {
        User user = userDao.getUserByEmail(userRequest.getEmail());
        if(user==null){
            log.warn("該信箱{}尚未被註冊",userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        //使用MD5，生成密碼的hash值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes());
        if(user.getPassword().equals(hashedPassword)){
            return user;
        }else {
            log.warn("email{}的密碼不正確",userRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
