package com.example.demo_spring_boot_mysql.model;

public class DeviceList {

    private Long id;
    private Integer parent;
    private String name;
    private Integer id_template;
    private Integer rtu_bus_address;
    private String tcp_gateway_ip;
    private Integer  tcp_gateway_port;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId_template() {
        return id_template;
    }

    public void setId_template(Integer id_template) {
        this.id_template = id_template;
    }

    public Integer getRtu_bus_address() {
        return rtu_bus_address;
    }

    public void setRtu_bus_address(Integer rtu_bus_address) {
        this.rtu_bus_address = rtu_bus_address;
    }

    public String getTcp_gateway_ip() {
        return tcp_gateway_ip;
    }

    public void setTcp_gateway_ip(String tcp_gateway_ip) {
        this.tcp_gateway_ip = tcp_gateway_ip;
    }

    public Integer getTcp_gateway_port() {
        return tcp_gateway_port;
    }

    public void setTcp_gateway_port(Integer tcp_gateway_port) {
        this.tcp_gateway_port = tcp_gateway_port;
    }
}
