package com.wang.service;

import com.wang.dao.UserRepository;
import com.wang.bean.User;
import com.wang.mapper.UserMapper;
import com.wang.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//使用 JPA 方式
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public User checkUser(String username, String password) {
//        boolean userMD5 = true; // 是否开启加密
//        User user = null;
//
//        if (userMD5) {
//            user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
//        } else {
//            user = userRepository.findByUsernameAndPassword(username, password);
//        }
//
//        return user;
//    }
//}

// 使用 Mabatis 方式
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {

        password = MD5Utils.code(password);
        User user = userMapper.getUserByNameAndPassword(username, password);

        return user;
    }
}
