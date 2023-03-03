package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.coding.springboot2.webadmin.liteflow.context.CustomContext;
import com.yomahub.liteflow.core.NodeBreakComponent;

@Component("g")
public class GCmp extends NodeBreakComponent {
    @Override
    public boolean processBreak() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("GCmp");
        CustomContext contextBean = this.getContextBean(CustomContext.class);
        return contextBean.increment() > 1;
    }

}
