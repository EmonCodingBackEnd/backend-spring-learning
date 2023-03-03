package com.coding.springboot2.webadmin.liteflow;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.coding.springboot2.webadmin.liteflow.context.AnotherContext;
import com.yomahub.liteflow.core.NodeComponent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("h")
public class HCmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        System.out.println("HCmp");

        AnotherContext anotherContext = this.getContextBean(AnotherContext.class);
        anotherContext.setMsg("我是HCmp");

        try {
            int i = 10 / 0;
        } catch (Exception e) {
            // 如果下面的异常未被捕获，会导致这里的isEnd=true失效，仍旧执行该节点后的节点。
            this.setIsEnd(true);
            log.error("错误啦", e);
            throw e;
        }
        // 如果上面抛出的异常未被捕获，会导致isEnd=true失效，仍旧执行该节点之后的节点。
        this.setIsEnd(true);
    }

    @Override
    public boolean isContinueOnError() {
        return true;
    }

}
