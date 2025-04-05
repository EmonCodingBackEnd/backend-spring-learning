package com.coding.boot3.openapi.swagger.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "部门信息", description = "部门实体类")
@Data
public class Dept {
    @Schema(description = "部门id")
    private Long id;
    @Schema(description = "部门名称")
    private String name;
}
