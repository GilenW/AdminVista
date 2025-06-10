package com.g.backend.controller;

import com.g.backend.common.Result;
import com.g.backend.entity.Employees;
import com.g.backend.service.EmployeesService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Resource
    private EmployeesService employeesService;

    @PostMapping("/login")
    public Result login(@RequestBody Employees employees) {
        Employees loginEmployee = employeesService.login(employees);
        return Result.success(loginEmployee);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Employees employees) {
        employeesService.register(employees);
        return Result.success();
    }



}

