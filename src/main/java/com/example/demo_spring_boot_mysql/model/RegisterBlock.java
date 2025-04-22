package com.example.demo_spring_boot_mysql.model;

public class RegisterBlock {
    private Long id;
    private String id_template;
    private Integer addr;
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getId_template() {
        return id_template;
    }

    public void setId_template(String id_template) {
        this.id_template = id_template;
    }

    public Integer getAddr() {
        return addr;
    }

    public void setAddr(Integer addr) {
        this.addr = addr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
