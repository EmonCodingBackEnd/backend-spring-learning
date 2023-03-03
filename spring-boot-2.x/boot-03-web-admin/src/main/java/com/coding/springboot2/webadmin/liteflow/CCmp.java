package com.coding.springboot2.webadmin.liteflow;

import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;

import java.util.concurrent.TimeUnit;

@Component("c")
public class CCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("CCmp");
    }
}
