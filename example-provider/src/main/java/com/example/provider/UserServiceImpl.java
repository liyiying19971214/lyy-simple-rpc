package com.example.provider;

import com.lyy.example.common.model.User;
import com.lyy.example.common.service.UserService;

/**
 * @author lyy
 * @Type UserServiceImpl.java
 * @Desc
 * @date 2024/12/5 21:52
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.printf("用户名"+user.getName());
        return user;
    }
}
