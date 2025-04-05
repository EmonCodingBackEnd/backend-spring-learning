package com.coding.boot3.core.controller;

import com.coding.boot3.core.controller.entity.UserEntity;
import com.coding.boot3.core.controller.service.AccountService;
import com.coding.boot3.core.controller.service.CouponService;
import com.coding.boot3.core.controller.service.SysService;
import com.coding.boot3.core.event.EventPublisher;
import com.coding.boot3.core.event.LoginSuccessEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 增加业务非常麻烦
 */
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;
    private final CouponService couponService;
    private final SysService sysService;
    private final EventPublisher eventPublisher;

    @GetMapping("/login")
    public String login(@RequestParam(value = "username", defaultValue = "问秋") String username, @RequestParam(value = "passwd", defaultValue = "123456") String passwd) {
        // 业务处理登录
        System.out.println("业务处理登录完成......");

        // 1、账户服务自动签到加积分
        accountService.addAccountScore(username, 10);
        // 2、优惠服务随机发放优惠券
        couponService.sendCoupon(username);
        // 3、系统服务登记用户登录的信息
        sysService.recordLog(username);
        // 设计模式：对新增开放，对修改关闭
        // XXX ...

        // 1、创建事件信息
        UserEntity userEntity = new UserEntity(username, passwd);
        LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent(userEntity);
        // 2、发送事件
        eventPublisher.sendEvent(loginSuccessEvent);
        return username + "登录成功";
    }
}
