package com.coding.boot3.core.controller.service;

import com.coding.boot3.core.controller.entity.UserEntity;
import com.coding.boot3.core.event.LoginSuccessEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value = 10)
public class AccountService implements ApplicationListener<LoginSuccessEvent> {
    public void addAccountScore(String username, int score) {
        System.out.println("addAccountScore:" + username + ":" + score);
    }

    @Override
    public void onApplicationEvent(LoginSuccessEvent event) {
        System.out.println("AccountService收到事件=====");
        UserEntity source = (UserEntity) event.getSource();
        System.out.println("AccountService.onApplicationEvent:" + source);
    }
}
