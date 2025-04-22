package com.example.demo_spring_boot_mysql.service;
import com.example.demo_spring_boot_mysql.mapper.RegisterBlockMapper;

import com.example.demo_spring_boot_mysql.model.RegisterBlock;
import com.example.demo_spring_boot_mysql.util.DB;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RegisterBlockService extends DB {
    public List<RegisterBlock> findAll() {
        try (SqlSession session = DB.getSession()) {
            RegisterBlockMapper mapper = session.getMapper(RegisterBlockMapper.class);
            return mapper.findAll();
        }
    }

    public RegisterBlock findById(Long id) {
        try (SqlSession session = DB.getSession()) {
            RegisterBlockMapper mapper = session.getMapper(RegisterBlockMapper.class);
            return mapper.findById(id);
        }
    }

    public void insert(RegisterBlock registerBlock) {
        try (SqlSession session = DB.getSession()) {
            RegisterBlockMapper mapper = session.getMapper(RegisterBlockMapper.class);
            mapper.insert(registerBlock);
            session.commit();
        }
    }

    public void update(Long id, RegisterBlock registerBlock) {
        try (SqlSession session = DB.getSession()) {
            RegisterBlockMapper mapper = session.getMapper(RegisterBlockMapper.class);
            registerBlock.setId(id);
            mapper.update(registerBlock);
            session.commit();
        }
    }

    public void delete(Long id) {
        try (SqlSession session = DB.getSession()) {
            RegisterBlockMapper mapper = session.getMapper(RegisterBlockMapper.class);
            mapper.delete(id);
            session.commit();
        }
    }


}
