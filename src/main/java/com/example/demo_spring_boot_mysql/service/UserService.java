package com.example.demo_spring_boot_mysql.service;
import com.example.demo_spring_boot_mysql.mapper.UserMapper;
import com.example.demo_spring_boot_mysql.util.Constants;
import com.example.demo_spring_boot_mysql.model.User;
import com.example.demo_spring_boot_mysql.util.DB;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;

import java.util.ArrayList;
import com.example.demo_spring_boot_mysql.util.JwtUtil;

@Service
public class UserService extends DB implements UserDetailsService {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.password(user.getPassword());
        builder.authorities(new ArrayList<>());

        return builder.build();
    }


    public User findByEmail(String email) {
        try (SqlSession session = getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findByEmail(email);
        }
    }
    public List<User> findAll() {
        try (SqlSession session = getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findAll();
        }
    }
    public User findById(Long id) {
        try (SqlSession session = getSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.findById(id);
        }
    }

    public void insert(User user) {
        try (SqlSession session = getSession()) {
            try {
                System.out.println("user.getPassword(): " + user.getPassword());
                String encryptedPassword = jwtUtil.encrypt(user.getPassword());
                System.out.println("encryptedPassword: " + encryptedPassword);
                user.setPassword(encryptedPassword);
            } catch (Exception e) {
                throw new RuntimeException("Failed to encrypt password", e);
            }
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insert(user);
            session.commit();

        }
    }

    public void update(Long id, User user) {
        SqlSession session = null;
        try  {
            session = getSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            user.setId(id);
            mapper.update(user);
            session.commit();

        } catch (Exception e)
        {
            if (session != null) {
                session.rollback();
            }
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void delete(Long id) {
        SqlSession session = null;
        try  {
            session = getSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.findById(id);
            if (user == null) {
                throw new RuntimeException("User not found with id: " + id);
            }
            mapper.delete(id);
            session.commit();

        } catch (Exception e)
        {
            if (session != null) {
                session.rollback();
            }
            throw e;
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
    }
    public boolean authenticate(String email, String password) throws Exception {

        User user = findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        String encryptedPassword = jwtUtil.encrypt(password);
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return true;
    }

}
