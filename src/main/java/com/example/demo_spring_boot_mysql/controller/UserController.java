package com.example.demo_spring_boot_mysql.controller;
import com.example.demo_spring_boot_mysql.model.User;
import com.example.demo_spring_boot_mysql.service.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.example.demo_spring_boot_mysql.util.Lib.JsonResponse;


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

    //   @PutMapping("/{id}")
    //   public void update(@PathVariable Long id, @RequestBody User user) {
    //
    //    try {
    //    	userService.update(id, user);
    //	} catch (Exception e) {
    //		// TODO: handle exception
    //	}
    //
    //   }
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
}
