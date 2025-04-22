package com.example.demo_spring_boot_mysql.mapper;
import  com.example.demo_spring_boot_mysql.model.DeviceList;
import java.util.List;
public interface DeviceListMapper {
    List<DeviceList> findAll();
    DeviceList findById(Long id);
    void insert(DeviceList deviceList);
    void update(DeviceList deviceList);
    void delete(Long id);


}