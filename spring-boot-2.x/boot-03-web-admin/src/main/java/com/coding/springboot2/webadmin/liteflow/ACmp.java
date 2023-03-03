package com.coding.springboot2.webadmin.liteflow;

import com.coding.springboot2.webadmin.liteflow.context.AnotherContext;
import com.yomahub.liteflow.annotation.LiteflowCmpDefine;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.annotation.LiteflowMethod;
import com.yomahub.liteflow.core.NodeComponent;
import com.yomahub.liteflow.enums.LiteFlowMethodEnum;
import com.yomahub.liteflow.enums.NodeTypeEnum;

@LiteflowComponent
@LiteflowCmpDefine(NodeTypeEnum.SWITCH)
public class ACmp {

    @LiteflowMethod(value = LiteFlowMethodEnum.PROCESS_SWITCH, nodeType = NodeTypeEnum.SWITCH)
    public String processSwitch(NodeComponent bindCmp) throws Exception {
        System.out.println("ACmp");
        AnotherContext contextBean = bindCmp.getContextBean(AnotherContext.class);
        System.out.println(contextBean);
        Object requestData = bindCmp.getRequestData();
        System.out.println("requestData=" + requestData);

        bindCmp.sendPrivateDeliveryData("b", "ACmp->BCmp的私有投递");
        return "aa";
    }

}
