package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeIfComponent;

@Component("e")
public class ECmp extends NodeIfComponent {

    @Override
    public boolean processIf() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        System.out.println("ECmp");
        return false;
    }

}
