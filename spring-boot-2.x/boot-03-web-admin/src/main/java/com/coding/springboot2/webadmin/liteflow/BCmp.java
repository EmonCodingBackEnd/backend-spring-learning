package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import com.coding.springboot2.webadmin.liteflow.context.AnotherContext;
import org.springframework.stereotype.Component;

import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.Slot;

@Component("b")
public class BCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        long start = System.currentTimeMillis();
        TimeUnit.SECONDS.sleep(3);
        long end = System.currentTimeMillis();
        System.out.println("BCmp:" + this.getPrivateDeliveryData());
        if (end - start > 2) {
            throw new Exception("节点BCmp已经超时了啊！");
        }
    }

    @Override
    public void onSuccess() throws Exception {
        System.out.println("牛逼，BCmp成功了！");
    }

    @Override
    public void onError() throws Exception {
        System.out.println("我靠，BCmp失败了？");
        AnotherContext contextBean = this.getContextBean(AnotherContext.class);
        contextBean.setMsg("BCmp失败了，启用隐式流程！");
        LiteflowResponse invoke = this.invoke2Resp("invoke", "代号：007");
        AnotherContext contextBean1 = invoke.getContextBean(AnotherContext.class);
        System.out.println(contextBean1);
    }

    @Override
    public <T> void afterProcess(String nodeId, Slot slot) {
        System.out.println("afterProcess被执行了");
    }
}
