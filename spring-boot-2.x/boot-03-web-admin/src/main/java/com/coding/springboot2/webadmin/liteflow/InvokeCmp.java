package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import com.coding.springboot2.webadmin.liteflow.context.AnotherContext;
import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;

@Component("invoke")
public class InvokeCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("InvokeCmp:" + this.getSubChainReqData());
        AnotherContext contextBean = this.getContextBean(AnotherContext.class);
        System.out.println(contextBean);
        contextBean.setMsg("报告！隐藏流程invoke了！");
    }
}
