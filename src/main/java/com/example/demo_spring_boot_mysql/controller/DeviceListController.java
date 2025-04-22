package com.example.demo_spring_boot_mysql.controller;
import com.example.demo_spring_boot_mysql.model.Customer;
import com.example.demo_spring_boot_mysql.model.DeviceList;
import com.example.demo_spring_boot_mysql.service.DeviceListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
public class DeviceListController {
    private final DeviceListService deviceListService;

    public DeviceListController(DeviceListService deviceListService) {
        this.deviceListService = deviceListService;
    }
    @GetMapping("/findAll")
    public List<DeviceList> findAll() {
        return deviceListService.findAll();
    }
    @GetMapping("/{id}")
    public DeviceList findById(@PathVariable Long id) {
        return deviceListService.findById(id);
    }

    @PostMapping
    public void insert(@RequestBody DeviceList device) {
        deviceListService.insert(device);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody DeviceList device) {
        deviceListService.update(id, device);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        deviceListService.delete(id);
    }
}
