package com.example.demo_spring_boot_mysql.mapper;
import  com.example.demo_spring_boot_mysql.model.RegisterBlock;
import java.util.List;
public interface  RegisterBlockMapper {
    List<RegisterBlock> findAll();
    RegisterBlock findById(Long id);
    void insert(RegisterBlock registerBlock);
    void update(RegisterBlock registerBlock);
    void delete(Long id);
}
