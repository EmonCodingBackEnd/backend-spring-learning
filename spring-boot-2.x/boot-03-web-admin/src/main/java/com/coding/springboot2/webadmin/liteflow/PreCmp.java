package com.coding.springboot2.webadmin.liteflow;

import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.core.NodeComponent;

@LiteflowComponent("pre")
public class PreCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        System.out.println("PreCmp");
        System.out.println("早于所有Cmp而执行！");
    }

}
