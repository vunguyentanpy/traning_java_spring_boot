package com.example.demo_spring_boot_mysql.service;
import com.example.demo_spring_boot_mysql.mapper.UserMapper;
import com.example.demo_spring_boot_mysql.model.User;
import com.example.demo_spring_boot_mysql.util.DB;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class UserService extends DB {


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
