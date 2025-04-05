package com.coding.boot3.openapi.swagger.controller;

import com.coding.boot3.openapi.swagger.entity.Dept;
import com.coding.boot3.openapi.swagger.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "部门管理", description = "部门管理相关接口")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    @Operation(summary = "根据id查询部门", description = "根据id查询部门详情")
    @GetMapping("/dept/{id}")
    public Dept getDept(@PathVariable("id") Long id) {
        return deptService.getDeptById(id);
    }

    @Operation(summary = "查询所有部门", description = "查询所有部门详情")
    @GetMapping("/depts")
    public List<Dept> getDept() {
        return deptService.getDepts();
    }

    @Operation(summary = "保存部门", description = "保存部门详情")
    @PostMapping("/dept")
    public String saveDept(@RequestBody Dept dept) {
        deptService.saveDept(dept);
        return "ok";
    }

    @Operation(summary = "根据id删除部门", description = "根据id删除部门详情")
    @DeleteMapping("/dept/{id}")
    public String deleteDept(@PathVariable("id") @Parameter(description = "部门id") Long id) {
        deptService.deleteDept(id);
        return "ok";
    }

}
