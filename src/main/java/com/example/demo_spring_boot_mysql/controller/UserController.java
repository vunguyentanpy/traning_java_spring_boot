package com.example.demo_spring_boot_mysql.controller;

import com.example.demo_spring_boot_mysql.model.User;
import com.example.demo_spring_boot_mysql.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo_spring_boot_mysql.util.Lib.JsonResponse;
import com.example.demo_spring_boot_mysql.util.JwtUtil;

import java.util.List;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final JwtUtil jwtUtil;
    private final UserService userService;
    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
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
   public ResponseEntity<Object> insert(@Valid @RequestBody User user, BindingResult result) {
       if (result.hasErrors()) {
           return ResponseEntity.badRequest().body(result.getAllErrors());
       }
       try {
           userService.insert(user);
           return ResponseEntity.ok(new JsonResponse(true, "Insert ok", user));
       } catch (Exception e) {
           return ResponseEntity.status(500).body(new JsonResponse(false, "Insert failed: " + e.getMessage(), null));
       }
   }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok(new JsonResponse(true, "Update ok", user));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new JsonResponse(false, "Update failed: " + e.getMessage(), null));
        }
    }
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable Long id) {
       try {
           userService.delete(id);
           return ResponseEntity.ok(new JsonResponse(true, "Delete ok", id));
       } catch (Exception e) {
           return ResponseEntity.status(500).body(new JsonResponse(false, "Delete failed: " + e.getMessage(), null));
       }
   }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        try {
            System.out.println("email: " + user.getEmail());
            boolean isAuthenticated = userService.authenticate(user.getEmail(), user.getPassword());
            if (isAuthenticated) {
                String token=null;
                token = jwtUtil.generateToken(user.getEmail());
                return ResponseEntity.ok(new JsonResponse(true, "Login successful", null,token));
            } else {
                return ResponseEntity.status(401).body(new JsonResponse(false, "Invalid credentials", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new JsonResponse(false, "Login failed: " + e.getMessage(), null));
        }
    }

}
