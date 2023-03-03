package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;

@Component("i")
public class ICmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("ICmp");
        // int i = 10 / 0;
    }

}
