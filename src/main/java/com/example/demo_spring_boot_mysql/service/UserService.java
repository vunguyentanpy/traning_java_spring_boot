package com.example.demo_spring_boot_mysql.service;
import com.example.demo_spring_boot_mysql.mapper.UserMapper;
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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.ArrayList;
import com.example.demo_spring_boot_mysql.util.JwtUtil;
@Service
public class UserService extends DB implements UserDetailsService {
    @Autowired
    private JwtUtil jwtUtil;

    public class PasswordEncryptionUtil {

        private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
        private static final String KEY = "0123456789abcdef";
        private static final String INIT_VECTOR = "abcdef9876543210";


        public static String encrypt(String password) throws Exception {
            try {
                SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes());

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

                byte[] encrypted = cipher.doFinal(password.getBytes());
                return Base64.getEncoder().encodeToString(encrypted);


            } catch (Exception ex) {
                return null;
            }

        }

        public static String decrypt(String message) throws Exception {
            try {
                SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes());

                Cipher cipher = Cipher.getInstance(ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

                byte[] decodedBytes = Base64.getDecoder().decode(message);
                byte[] decrypted = cipher.doFinal(decodedBytes);

                return new String(decrypted);
            } catch (Exception ex) {

                return null;

            }
        }

    }

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

    public boolean authenticate(String email, String password) throws Exception {

        User user = findByEmail(email);

        if (user != null) {
            String encryptedPassword = PasswordEncryptionUtil.encrypt(password);
            String password_encrypt = PasswordEncryptionUtil.decrypt(encryptedPassword);
            System.out.println("encryptedPassword: " + password_encrypt);
            return encryptedPassword.equals(user.getPassword());
        }
        return false;
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
                String encryptedPassword = PasswordEncryptionUtil.encrypt(user.getPassword());
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


}
