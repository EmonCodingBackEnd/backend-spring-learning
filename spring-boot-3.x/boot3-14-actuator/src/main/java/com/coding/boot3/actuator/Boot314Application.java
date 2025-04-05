package com.coding.boot3.actuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 整合 Prometheus+Grafana 完成线上应用指标采集和监控系统
 * 1、改造SpringBoot应用，产生Prometheus需要的格式数据
 *  - 导入 micrometer-registry-prometheus
 * 2、部署java应用。在同一个机器内，访问 http://192.168.20.116:8080/actuator/prometheus 就能得到指标数据
 * 3、修改Prometheus配置，让它拉取某个应用的指标数据。
 */
@SpringBootApplication
public class Boot314Application {

    public static void main(String[] args) {
        SpringApplication.run(Boot314Application.class, args);
    }

}