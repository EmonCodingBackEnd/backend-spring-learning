package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.coding.springboot2.webadmin.liteflow.context.AnotherContext;
import com.yomahub.liteflow.core.NodeComponent;

@Component("j")
public class JCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("JCmp");

        AnotherContext anotherContext = this.getContextBean(AnotherContext.class);
        System.out.println("JCmp==>打印anotherContext的内容是：" + anotherContext.getMsg());
    }
}
