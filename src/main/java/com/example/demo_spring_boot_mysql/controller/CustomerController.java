package com.example.demo_spring_boot_mysql.controller;



import com.example.demo_spring_boot_mysql.model.Customer;
import com.example.demo_spring_boot_mysql.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
    @GetMapping("/findAll")
    public List<Customer> findAll() {
        return customerService.findAll();
    }
   @GetMapping("/{id}")
   public Customer findById(@PathVariable Long id) {
       return customerService.findById(id);
   }

   @PostMapping
   public void insert(@RequestBody Customer customer) {
       customerService.insert(customer);
   }

   @PutMapping("/{id}")
   public void update(@PathVariable Long id, @RequestBody Customer customer) {
       customerService.update(id, customer);
   }

   @DeleteMapping("/{id}")
   public void delete(@PathVariable Long id) {
       customerService.delete(id);
   }
}

