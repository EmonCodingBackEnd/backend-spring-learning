package com.coding.springboot2.webadmin.liteflow;

import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;

import java.util.concurrent.TimeUnit;

@Component("d")
public class DCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("DCmp");
    }
}
