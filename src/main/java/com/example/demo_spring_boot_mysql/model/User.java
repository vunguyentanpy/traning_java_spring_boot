package com.example.demo_spring_boot_mysql.model;

public class User {
    private Long id;
    private String email;
    private String first_name;
    private String last_name;
    private String phone;
    private String create_date;
    private String status;
    
    private String password;
    private String salt;
    private String last_login;
    private String create_by;
    private String updated_date;
    private String updated_by;

    public Long getId() { return id; }
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirst_name() { return first_name; }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    
    public String getLast_name() { return last_name; }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() { return salt; }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreate_date() { return create_date; }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getLast_login() { return last_login; }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getCreate_by() { return create_by; }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }


    public String getUpdated_date() { return updated_date; }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }


    public String getUpdated_by() { return updated_by; }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    
}
