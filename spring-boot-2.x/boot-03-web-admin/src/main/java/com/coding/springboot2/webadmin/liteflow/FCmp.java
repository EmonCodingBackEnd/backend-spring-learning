package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;

@Component("f")
public class FCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("FCmp");
    }

}
