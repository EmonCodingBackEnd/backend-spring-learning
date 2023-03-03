package com.coding.springboot2.webadmin.liteflow;

import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.coding.springboot2.webadmin.liteflow.context.AnotherContext;
import com.coding.springboot2.webadmin.liteflow.context.CustomContext;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.flow.entity.CmpStep;

@Component
public class YourClass {

    @Resource
    private FlowExecutor flowExecutor;

//    @PostConstruct
    public void testConfig() {
        CustomContext context = new CustomContext();
        AnotherContext anotherContext = new AnotherContext();
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg", context, anotherContext);
        if (!response.isSuccess()) {
            System.out.println(ExceptionUtils.getMessage(response.getCause()));
        }
        String message = response.getMessage();
        // 9
        Map<String, CmpStep> executeSteps = response.getExecuteSteps();
        // 13
        Queue<CmpStep> executeStepQueue = response.getExecuteStepQueue();
        // pre==>LOOP_5==>a==>e==>f==>g==>a==>e==>f==>g==>h==>i==>finally
        String executeStepStr = response.getExecuteStepStr();
        // pre==>LOOP_5==>a==>e==>f==>g==>a==>e==>f==>g==>h==>i==>finally
        String executeStepStrWithoutTime = response.getExecuteStepStrWithoutTime();
        // pre<2>==>LOOP_5<0>==>a<5>==>e<5001>==>f<1000>==>g<1000>==>a<0>==>e<4999>==>f<1000>==>g<1000>==>h<1000>==>i<1000>==>finally<0>
        String executeStepStrWithTime = response.getExecuteStepStrWithTime();
        // CustomContext contextBean = response.getContextBean(CustomContext.class);
        System.out.println(message);
    }
}
