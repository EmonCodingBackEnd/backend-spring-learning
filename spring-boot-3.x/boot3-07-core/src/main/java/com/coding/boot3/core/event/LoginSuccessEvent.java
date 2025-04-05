package com.coding.boot3.core.event;

import com.coding.boot3.core.controller.entity.UserEntity;
import org.springframework.context.ApplicationEvent;

/**
 * 登录成功事件。所有事件都推荐继承 ApplicationEvent，这样在监听的时候，可以强转成对应的事件类型
 */

public class LoginSuccessEvent extends ApplicationEvent {

    /**
     * @param source 代表是谁登录成功了
     */
    public LoginSuccessEvent(UserEntity source) {
        super(source);
    }
}
