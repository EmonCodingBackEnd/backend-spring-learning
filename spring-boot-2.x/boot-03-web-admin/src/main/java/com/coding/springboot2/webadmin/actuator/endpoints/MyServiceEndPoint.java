package com.coding.springboot2.webadmin.actuator.endpoints;

import java.util.Collections;
import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "myService")
public class MyServiceEndPoint {

    // http://localhost:8080/actuator/myService
    @ReadOperation
    public Map<String, Object> getDockerInfo() {
        return Collections.singletonMap("name", "myService");
    }

    @WriteOperation
    public void stopDocker() {
        System.out.println("docker stoped!");
    }
}
