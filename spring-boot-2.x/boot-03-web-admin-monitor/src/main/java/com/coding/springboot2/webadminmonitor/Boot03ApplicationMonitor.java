package com.coding.springboot2.webadminmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

// SBA beg
@EnableAdminServer
// SBA end
@SpringBootApplication
public class Boot03ApplicationMonitor {
    public static void main(String[] args) {
        SpringApplication.run(Boot03ApplicationMonitor.class);
    }
}
