package com.example.demo_spring_boot_mysql.controller;
import com.example.demo_spring_boot_mysql.model.Customer;
import com.example.demo_spring_boot_mysql.model.RegisterBlock;
import com.example.demo_spring_boot_mysql.service.RegisterBlockService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registerBlock")
public class RegisterBlockController {
    private final RegisterBlockService registerBlockService;

    public RegisterBlockController(RegisterBlockService registerBlockService) {
        this.registerBlockService = registerBlockService;
    }
//    @GetMapping
//    public List<RegisterBlock> findAll() {
//        return registerBlockService.findAll();
//    }
    @GetMapping("/findAll")
    public List<RegisterBlock> findAll() {
        System.out.println("findAll: ");
        return registerBlockService.findAll();
    }

    @GetMapping("/{id}")
    public RegisterBlock findById(@PathVariable Long id) {

        return registerBlockService.findById(id);
    }

    @PostMapping
    public void insert(@RequestBody RegisterBlock registerBlock) {
        registerBlockService.insert(registerBlock);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody RegisterBlock registerBlock) {
        registerBlockService.update(id, registerBlock);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        registerBlockService.delete(id);
    }
}
