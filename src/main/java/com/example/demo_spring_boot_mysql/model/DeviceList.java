package com.example.demo_spring_boot_mysql.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceList {

     Long id;
     Integer parent;
     String name;
     Integer id_template;
     Integer rtu_bus_address;
     String tcp_gateway_ip;
     Integer  tcp_gateway_port;


}
