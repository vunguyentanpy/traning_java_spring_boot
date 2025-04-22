package com.example.demo_spring_boot_mysql.service;

import com.example.demo_spring_boot_mysql.mapper.DeviceListMapper;

import com.example.demo_spring_boot_mysql.model.DeviceList;
import com.example.demo_spring_boot_mysql.util.DB;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DeviceListService extends DB{

    public List<DeviceList> findAll() {
        try (SqlSession session = DB.getSession()) {
            DeviceListMapper mapper = session.getMapper(DeviceListMapper.class);
            return mapper.findAll();
        }
    }
    public DeviceList findById(Long id) {
        try (SqlSession session = DB.getSession()) {
            DeviceListMapper mapper = session.getMapper(DeviceListMapper.class);
            return mapper.findById(id);
        }
    }

    public void insert(DeviceList device) {
        try (SqlSession session = DB.getSession()) {
            DeviceListMapper mapper = session.getMapper(DeviceListMapper.class);
            mapper.insert(device);
            session.commit();
        }
    }

    public void update(Long id, DeviceList device) {
        try (SqlSession session = DB.getSession()) {
            DeviceListMapper mapper = session.getMapper(DeviceListMapper.class);
            device.setId(id);
            mapper.update(device);
            session.commit();
        }
    }

    public void delete(Long id) {
        try (SqlSession session = DB.getSession()) {
            DeviceListMapper mapper = session.getMapper(DeviceListMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }

}
