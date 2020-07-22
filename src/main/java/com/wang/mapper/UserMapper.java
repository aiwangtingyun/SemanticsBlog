package com.wang.mapper;

import com.wang.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

//@Mapper
public interface UserMapper {

    //@Select("select * from t_user where username=#{username} and password=#{password}")
    public User getUserByNameAndPassword(@Param("username") String username, @Param("password") String password);
}
