package com.coding.boot3.openapi.swagger.service;

import com.coding.boot3.openapi.swagger.entity.Dept;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DeptService {

    Map<Long, Dept> data = new ConcurrentHashMap<>();

    public Dept getDeptById(Long id) {
        return data.get(id);
    }

    public List<Dept> getDepts() {
        return data.values().stream().toList();
    }

    public void saveDept(Dept dept) {
        data.put(dept.getId(), dept);
    }

    public void deleteDept(Long id) {
        data.remove(id);
    }
}
