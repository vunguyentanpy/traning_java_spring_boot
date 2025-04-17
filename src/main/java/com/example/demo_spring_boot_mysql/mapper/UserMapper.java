package com.example.demo_spring_boot_mysql.mapper;

import  com.example.demo_spring_boot_mysql.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
public interface UserMapper {
    List<User> findAll();
    User findById(Long id);
    void insert(User user);
    void update(User user);
    void delete(Long id);
}
