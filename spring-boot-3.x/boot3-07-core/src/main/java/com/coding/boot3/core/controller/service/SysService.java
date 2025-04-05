package com.coding.boot3.core.controller.service;

import com.coding.boot3.core.controller.entity.UserEntity;
import com.coding.boot3.core.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class SysService {

    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    @EventListener
    public void onEvent(LoginSuccessEvent event) {
        System.out.println("SysService收到事件=====");
        UserEntity source = (UserEntity) event.getSource();
        System.out.println("SysService.onApplicationEvent:" + source);
    }

    public void recordLog(String username) {
        System.out.println(username + "登录信息已被记录");
    }
}
