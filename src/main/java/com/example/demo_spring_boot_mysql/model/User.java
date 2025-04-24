package com.example.demo_spring_boot_mysql.model;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
     Long id;
    @Email(message = "Email should be valid")
     String email;
    @NotBlank(message = "First name is mandatory")
     String first_name;
    @NotBlank(message = "Last name is mandatory")
     String last_name;

     String phone;
     String create_date;
     String status;
    
     String password;
     String salt;
     String last_login;
     String create_by;
     String updated_date;
     String updated_by;

}
