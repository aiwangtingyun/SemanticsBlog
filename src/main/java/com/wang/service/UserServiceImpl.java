package com.wang.service;

import com.wang.bean.User;
import com.wang.dao.UserRepository;
import com.wang.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {

        password = MD5Utils.code(password);
        User user = userRepository.findByUsernameAndPassword(username, password);

        return user;
    }
}
