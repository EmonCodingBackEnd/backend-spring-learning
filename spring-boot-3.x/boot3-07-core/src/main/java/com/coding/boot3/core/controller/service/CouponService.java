package com.coding.boot3.core.controller.service;

import com.coding.boot3.core.controller.entity.UserEntity;
import com.coding.boot3.core.event.LoginSuccessEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    @EventListener
    public void onEvent(LoginSuccessEvent event) {
        System.out.println("CouponService收到事件=====");
        UserEntity source = (UserEntity) event.getSource();
        System.out.println("CouponService.onApplicationEvent:" + source);
    }

    public void sendCoupon(String username) {
        System.out.println(username + "随机得到了优惠券");
    }


}
