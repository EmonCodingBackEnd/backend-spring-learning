package com.coding.boot3.core.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher implements ApplicationEventPublisherAware {

    /**
     * 真正发事件的底层组件,SpringBoot会通过ApplicationEventPublisherAware接口注入进来
     */
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 所有的事件都是ApplicationEvent的子类
     *
     * @param event -
     */
    public void sendEvent(ApplicationEvent event) {
        // 调用底层API发送事件
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * 会被自动调用，把真正发事件的底层组件给我们注入进来
     *
     * @param applicationEventPublisher event publisher to be used by this object
     */
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
