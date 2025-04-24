package com.example.demo_spring_boot_mysql.controller;

import com.example.demo_spring_boot_mysql.model.User;
import com.example.demo_spring_boot_mysql.service.UserService;
import com.example.demo_spring_boot_mysql.exception.GlobalExceptionHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo_spring_boot_mysql.util.Lib.JsonResponse;
import com.example.demo_spring_boot_mysql.util.JwtUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final  JwtUtil jwtUtil;
    private final UserService userService;
    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/all")
    public ResponseEntity<Object> findAll() {
        try {
            List<User> users = userService.findAll();
            return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true,
                    "Get all ok", users));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GlobalExceptionHandler.JsonResponse(false,
                            "Get all failed: " + e.getMessage(), null));
        }

    }
   @GetMapping("/{id}")
   public ResponseEntity<Object> findById(@PathVariable Long id) {
       try {
           User  user = userService.findById(id);
           return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true,
                   "findById ok", user));
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new GlobalExceptionHandler.JsonResponse(false,
                           "findById failed: " + e.getMessage(), null));
       }
   }

   @PostMapping("/insert")
   public ResponseEntity<Object> insert(@Valid @RequestBody User user, BindingResult result) {
       if (result.hasErrors()) {
           return ResponseEntity.badRequest().body(result.getAllErrors());
       }
       try {
           userService.insert(user);
           return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true,
                   "Insert ok", user));
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(new GlobalExceptionHandler.JsonResponse(false,
                           "Insert failed: " + e.getMessage(), null));
       }
   }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.update(id, user);
            return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true,
                    "Update ok", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GlobalExceptionHandler.JsonResponse(false,
                            "Update failed: " + e.getMessage(), null));
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestBody JwtUtil.RefreshTokenRequest request) {
        try {
            System.out.println("refreshToken " + request.getRefreshToken());
            String newAccessToken = jwtUtil.refreshAccessToken(request.getRefreshToken());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("accessToken", newAccessToken);

            return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true,
                    "Token refreshed successfully", responseData));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GlobalExceptionHandler.JsonResponse(false,
                            "Refresh token failed: " + e.getMessage(), null));
        }
    }
   @DeleteMapping("/{id}")
   public ResponseEntity<Object> delete(@PathVariable Long id) {
       userService.delete(id);
       return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true, "Delete ok", id));
   }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user)  {
        try {
            boolean isAuthenticated = userService.authenticate(user.getEmail(), user.getPassword());
            if (isAuthenticated) {
                String accessToken = jwtUtil.generateAccessToken(user.getEmail());
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
                Map<String, Object> responseData = new HashMap<>();
                responseData.put("accessToken", accessToken);
                responseData.put("refreshToken", refreshToken);

                return ResponseEntity.ok(new GlobalExceptionHandler.JsonResponse(true,
                        "Login successful", responseData));
            } else {
                throw new IllegalArgumentException("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GlobalExceptionHandler.JsonResponse(false,
                            "Login failed: " + e.getMessage(), null));
        }
    }

}
