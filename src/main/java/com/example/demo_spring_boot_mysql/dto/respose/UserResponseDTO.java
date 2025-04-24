package com.example.demo_spring_boot_mysql.dto.respose;
import com.example.demo_spring_boot_mysql.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {
     Long id;
     String email;
     String first_name;
     String last_name;

     String phone;
     String create_date;
     String status;

     String last_login;
     String create_by;
     String updated_date;
     String updated_by;



    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.first_name = user.getFirst_name();
        this.last_name = user.getLast_name();
        this.phone = user.getPhone();
        this.create_date = user.getCreate_date();
        this.status = user.getStatus();
        this.last_login = user.getLast_login();
        this.create_by = user.getCreate_by();
        this.updated_date = user.getUpdated_date();
        this.updated_by = user.getUpdated_by();

    }




}
