package com.coding.boot3.openapi.swagger.service;

import com.coding.boot3.openapi.swagger.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EmployeeService {

    Map<Long, Employee> data = new ConcurrentHashMap<>();

    public Employee getEmployeeById(Long id) {
        return data.get(id);
    }

    public List<Employee> getEmployees() {
        return data.values().stream().toList();
    }

    public void saveEmployee(Employee employee) {
        data.put(employee.getId(), employee);
    }

    public void deleteEmployee(Long id) {
        data.remove(id);
    }
}
