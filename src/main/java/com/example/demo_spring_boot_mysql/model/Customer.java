package com.example.demo_spring_boot_mysql.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
     Long id;
     String name;
     String email;
     String createdAt;
     String updatedAt;


}