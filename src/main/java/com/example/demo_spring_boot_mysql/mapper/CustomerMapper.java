package com.example.demo_spring_boot_mysql.mapper;

import  com.example.demo_spring_boot_mysql.model.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CustomerMapper {

    List<Customer> getAllCustomers();
    List<Customer> findAll();
    Customer findById(Long id);
    void insert(Customer customer);
    void update(Customer customer);
    void delete(Long id);
}
