package com.example.demo_spring_boot_mysql.service;

import com.example.demo_spring_boot_mysql.mapper.CustomerMapper;
import com.example.demo_spring_boot_mysql.model.Customer;
import com.example.demo_spring_boot_mysql.util.DB;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CustomerService extends DB {

    public List<Customer> getAllCustomers() {
        try (SqlSession session = getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getAllCustomers();
        }
    }
    public List<Customer> findAll() {
        try (SqlSession session = getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.findAll();
        }
    }
   public Customer findById(Long id) {
       try (SqlSession session = getSession()) {
           CustomerMapper mapper = session.getMapper(CustomerMapper.class);
           return mapper.findById(id);
       }
   }

   public void insert(Customer customer) {
       try (SqlSession session = getSession()) {
           CustomerMapper mapper = session.getMapper(CustomerMapper.class);
           mapper.insert(customer);
           session.commit();
       }
   }

   public void update(Long id, Customer customer) {
       try (SqlSession session = getSession()) {
           CustomerMapper mapper = session.getMapper(CustomerMapper.class);
           customer.setId(id);
           mapper.update(customer);
           session.commit();
       }
   }

   public void delete(Long id) {
       try (SqlSession session = getSession()) {
           CustomerMapper mapper = session.getMapper(CustomerMapper.class);
           mapper.delete(id);
           session.commit();
       }
   }


}