package com.example.demo_spring_boot_mysql.controller;
import com.example.demo_spring_boot_mysql.model.User;
import com.example.demo_spring_boot_mysql.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }
   @GetMapping("/{id}")
   public User findById(@PathVariable Long id) {
       return userService.findById(id);
   }

   @PostMapping
   public void insert(@RequestBody User user) {
    userService.insert(user);
   }

   @PutMapping("/{id}")
   public void update(@PathVariable Long id, @RequestBody User user) {
    userService.update(id, user);
   }

   @DeleteMapping("/{id}")
   public void delete(@PathVariable Long id) {
    userService.delete(id);
   }
}
