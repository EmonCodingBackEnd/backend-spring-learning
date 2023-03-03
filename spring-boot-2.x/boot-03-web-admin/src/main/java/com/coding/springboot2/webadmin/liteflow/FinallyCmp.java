package com.coding.springboot2.webadmin.liteflow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

@LiteflowComponent("finally")
public class FinallyCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        System.out.println("FinallyCmp");
        System.out.println("晚于所有Cmp而执行！");
        System.out.println(this.getTag());
    }

}
